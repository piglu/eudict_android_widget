B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=7.3
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim rijecG As List
	Dim prijevodG As List
	Dim pojamHRVenc, pojamHRVencTekst As String
	Dim pojamActa As String
	Dim pojamActaOpis As String
	Dim stranaRijec As String
	Dim stranaRijecOpis As String
	Dim strana_rijec_link As String
	Dim hrv_enc_link As String
	Dim z_acta_link As String
	Dim z_eudict_link As String
	
	' hrvatski jezični portal -> job2.PostString("http://hjp.znanje.hr/index.php?show=search", "word=indiferentan") -> _
	'    <meta name="description" content="indiferentan: indif&#x00E8;rentan prid. &#x2329;odr. -tn&#x012B;&#x232A; : koji ne pokazuje svoju naklonost &#x005B;indiferentan na to; indiferentan prema tome&#x005D;; neutralan, nezainteresiran, ravnodu&#x0161;an " />
	' acta croatica
	' predak -> https://actacroatica.com/hr/dosearch?q=jela+stanko
	' istraži prezime i ime -> https://actacroatica.com/hr/dosearch?q=jela+stanko&from=surname
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private DetailsDialog As CustomLayoutDialog
'	Dim link As String = "https://eudict.com/?lang=croeng&word=podnaslov"
	Dim edtRijec As FloatLabeledEditText'EditText
	Private imgPromijeni As ImageView
'	Dim sax As SaxParser
'	Dim tid As Tidy
	Dim js As jSoup
'	Dim DOM1 As List
	Dim Extract1 As List
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	rijecG.Initialize
	prijevodG.Initialize

	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")
	If mapa.Get("eudict") Then
		PokaziDlgTrazi
	Else If mapa.Get("HRVenc") Then
		PokaziDlgTraziHRVEnc
	Else if mapa.Get("acta") Then
		PokaziDlgTraziActa
	Else
		PokaziDlgTraziStranuRijec
	End If
End Sub

Sub Activity_Resume
	Dim p As Phone

	p.SetScreenOrientation(1)
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub PokaziDlgTraziStranuRijec
	Dim sf As Object = DetailsDialog.ShowAsync("Riječnik stranih riječi", "U redu", "Odbaci", "", LoadBitmap(File.DirAssets, "form.png"), True)
	DetailsDialog.SetSize(100%x, 50%y)
	Wait For (sf) Dialog_Ready(pnl As Panel)
	pnl.LoadLayout("dlg_trazi")
	Wait For (sf) Dialog_Result(res As Int)
	If res = DialogResponse.POSITIVE Then
		PosaljiUpitStranaRijec(edtRijec.Text)
	Else
		Activity.Finish
	End If
End Sub

Sub PosaljiUpitStranaRijec(rijec As String)
	Dim j As HttpJob
	ProgressDialogShow("Šaljem upit...")
	j.Initialize("", Me)
	j.PostString("http://hjp.znanje.hr/index.php?show=search", "word=" & rijec)
	strana_rijec_link = "http://hjp.znanje.hr/index.php?show=search?word=" & rijec
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		If j.GetString.Contains("Nijedna natuknica u rječničkoj bazi ne zadovoljava zadane uvjete.") = False Then
			ParsajStranicuStranaRijec(j.GetString)
		Else
			Msgbox2Async("Nijedna natuknica u rječničkoj bazi ne zadovoljava zadane uvjete.", "Info", "U redu", "", "", Null, False)
			Wait For Msgbox_Result (Result As Int)
			If Result = DialogResponse.POSITIVE Then
				PokaziDlgTraziStranuRijec
			End If
		End If
	Else
		Log("neka greška!")
	End If
		
	j.Release
	ProgressDialogHide
End Sub

Sub ParsajStranicuStranaRijec(str As String)
	Dim matcher1 As Matcher

	stranaRijecOpis = ""

	str = str.Replace("&#x005B;", "[").Replace("&#x005D;", "]").Replace("<i>", "").Replace("</i>", "").Replace("&#x2329;", "〈").Replace("&#x232A;", "〉").Replace("&#x012B;", "ī").Replace("&#x00E8;", "è"). _
		  Replace("&#x00EC;", "ì").Replace("&#x017E;", "ž").Replace("&#x0161;", "š").Replace("&#x010D;", "č").Replace("&#x0107;", "ć").Replace("&#x00E9;", "é")

	matcher1 = Regex.Matcher($"<meta name="description" content=".*?: (.*?) : .*?" />"$, str)
	Do While matcher1.Find
		stranaRijec = matcher1.Group(1)
	Loop

	Dim l As List = Regex.Split("<!-- definicija -->", str)
	For i = 1 To l.Size - 1
		Dim ss As String = Regex.Replace($"<\/?\w+((\s+\w+(\s*=\s*(?:\".*?"|'.*?'|[^'\">\s]+))?)+\s*|\s*)\/?>"$, l.Get(i), "")
		stranaRijecOpis = ss.SubString2(0, ss.IndexOf("<!-- sintagma -->"))
	Next

	CallSubDelayed(eudict_servis, "UbaciStranuRijec")
	Activity.Finish
End Sub

Sub PokaziDlgTraziHRVEnc
'	Log("PokaziDlgTraziHRVEnc")
	'http://www.enciklopedija.hr/Trazi.aspx?t=janko%20dra%C5%A1kovi%C4%87&s=0&k=10
	Dim sf As Object = DetailsDialog.ShowAsync("Hrvatska enciklopedija", "U redu", "Odbaci", "", LoadBitmap(File.DirAssets, "form.png"), True)
	DetailsDialog.SetSize(100%x, 50%y)
	Wait For (sf) Dialog_Ready(pnl As Panel)
	pnl.LoadLayout("dlg_trazi")
	Wait For (sf) Dialog_Result(res As Int)
	If res = DialogResponse.POSITIVE Then
		PosaljiUpitHRVEnc(edtRijec.Text)
	Else
		Activity.Finish
	End If
End Sub

Sub PosaljiUpitHRVEnc(pojam As String)
	Dim j As HttpJob
	ProgressDialogShow("Šaljem upit...")
	j.Initialize("", Me)
	j.Download2("http://www.enciklopedija.hr/Trazi.aspx", Array As String("t", pojam, "s", "0", "k", "10"))
	hrv_enc_link = "http://www.enciklopedija.hr/Trazi.aspx&t=" & pojam & "&t=0&k=10"
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		ParsajStranicuHRVencZaLink(j.GetString)
	Else
		Log("neka greška")
	End If
		
	j.Release
	ProgressDialogHide
End Sub

Sub ParsajStranicuHRVencZaLink(str As String)
	Dim matcher1 As Matcher
	Dim linkHRVenc As String

	'http://www.enciklopedija.hr/natuknica.aspx?id=16192
	matcher1 = Regex.Matcher($"<div id="ContentPlaceHolder1_divListaPogodaka"><p class="pogodak"><a class=".*?" href="(.*?)"><b>.*?</b></a>"$, str)
	Do While matcher1.Find
		linkHRVenc = "http://www.enciklopedija.hr/" & matcher1.Group(1)
	Loop

'	Log("linkHRVenc: " & linkHRVenc)

	Dim j As HttpJob
	ProgressDialogShow("Šaljem upit...")
	j.Initialize("", Me)
'	Log(linkHRVenc)
	j.Download(linkHRVenc)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		ParsajStranicuHRVencZaPojam(j.GetString)
	Else
		ToastMessageShow("Neka greška!!", False)
	End If

	j.Release
	ProgressDialogHide
End Sub

Sub ParsajStranicuHRVencZaPojam(str As String)
	Dim matcher1 As Matcher
	Dim local_html As String

	pojamHRVenc = ""
	pojamHRVencTekst = ""

	matcher1 = Regex.Matcher($"<div id="ContentPlaceHolder1_Naslov"><h1>(.*?)</h1></div>"$, str)
	Do While matcher1.Find
		pojamHRVenc = matcher1.Group(1)
	Loop

'	str = str.Replace("&scaron;", "š").Replace("&nbsp;", "").Replace("&ndash;", "-").Replace("&Scaron;", "Š").Replace("&raquo;", "»").Replace("&laquo;", "«"). _
'		  Replace($"<p><span style="color: #8b2323"><b>"$, "").Replace("</b></span>", "").Replace($"<span style="color: #000000"><i>"$, "").Replace("<nac>", ""). _
'		  Replace("</nac>", "").Replace("<dj>", "").Replace("</dj>", "").Replace("<mr>", "").Replace("</mr>", "").Replace("</i></span>", "").Replace("</i></span></djela>", ""). _
'		  Replace("</djela>", "").Replace($"<djela><span style="color: #000000"><i>"$, "").Replace("<dr>", "").Replace("</dr>", "").Replace("<ms>", "").Replace("</ms>", ""). _
'		  Replace("<ds>", "").Replace("</ds>", "").Replace("<b>", "").Replace("</b>", "").Replace($"<span style="color: #000080">"$, "").Replace("</p>", "").Replace("</div>", ""). _
'		  Replace($"<p><span style="color:#8B2323">"$, "").Replace($"&Ouml;"$, "Ö").Replace($"&icirc;"$, "î").Replace("<p>", "")
	str = str.Replace("&scaron;", "š").Replace("&ndash;", "-").Replace("&Scaron;", "Š").Replace("&raquo;", "»").Replace("&laquo;", "«").Replace($"&Ouml;"$, "Ö").Replace($"&icirc;"$, "î")

'	Dim l As List = Regex.Split($"<div id="ContentPlaceHolder1_Tekst" class="clanak"><a name="start"></a><div>"$, str)
'	Dim s As String = l.Get(1)
'	Log(s)
	File.WriteString(Starter.FilePath, "tmp.html", str)'s)
	' DOM methods
'	DOM1.Initialize
	local_html = File.ReadString(Starter.FilePath, "tmp.html")

'	DOM1 = js.selectorElementText(local_html, "span")
'	For i = 0 To DOM1.Size -1
'		Log(DOM1.Get(i))
'	Next

'	Log(local_html)
'	DOM1 = js.getElementsByTag(local_html, "p")
'	For i = 0 To DOM1.Size -1
'		Log(DOM1.Get(i))
'		Log(DOM2.Get(i))
'	Next

	' Extract Attributes, text & HTML
'	html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>"
	Extract1.Initialize
	Extract1 = js.selectorElementText(local_html, "p")
	Dim s As String
	For i = 0 To Extract1.Size - 1
'		Log(Extract1.Get(i))
		s = s & Extract1.Get(i)
	Next
'	Extract2 = js.selectorElementAttr(local_html, "a", "href")

'	If s.Contains($"<a href=""$) Then
'		matcher1 = Regex.Matcher($"(.*?)<a href=".*?">+"$, s)
'		Do While matcher1.Find
'			pojamHRVencTekst = pojamHRVencTekst & matcher1.Group(1)
'		Loop
'		If s.Contains("</a>") Then
'			pojamHRVencTekst = pojamHRVencTekst.Replace("</a>", "")
'		End If
'	Else
	pojamHRVencTekst = s
'	End If
'
	CallSubDelayed(eudict_servis, "UbaciHRVencPojam")
	Activity.Finish
End Sub

Sub PokaziDlgTraziActa
	Log("PokaziDlgTraziActa")
	Dim sf As Object = DetailsDialog.ShowAsync("Acta Croatica", "U redu", "Odbaci", "", LoadBitmap(File.DirAssets, "form.png"), True)
	DetailsDialog.SetSize(100%x, 50%y)
	Wait For (sf) Dialog_Ready(pnl As Panel)
	pnl.LoadLayout("dlg_trazi")
	'0x00002000 = TYPE_TEXT_FLAG_CAP_WORDS (capitalize first character of each word)
'	edtRijec.EditText.InputType = Bit.Or(0x00002000, edtRijec.EditText.InputType)
'	DialogLastName.EditText.InputType = Bit.Or(0x00002000, DialogLastName.EditText.InputType)
'	DialogAge.Add("")
'	For i = 1 To 120
'		DialogAge.Add(i)
'	Next
'	DetailsDialog.GetButton(DialogResponse.POSITIVE).Enabled = False
	Wait For (sf) Dialog_Result(res As Int)
	If res = DialogResponse.POSITIVE Then
'		Log(edtRijec.Text)
		PosaljiUpitActaCroatica(edtRijec.Text)
'		ToastMessageShow($"${DialogFirstName.Text} ${DialogLastName.Text} is ${DialogAge.SelectedItem} years old"$, True)
	Else
		Activity.Finish
	End If
End Sub

Sub PosaljiUpitActaCroatica(rijec As String)
	Dim j As HttpJob

	pojamActa = rijec

	ProgressDialogShow("Šaljem upit...")
	j.Initialize("", Me)
	j.Download("https://actacroatica.com/hr/dosearch?q=" & rijec)
	z_acta_link = "https://actacroatica.com/hr/dosearch?q=" & rijec
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		If j.GetString.Contains("Još nema spominjanja u izvorima") = False Then
'			Log(j.GetString)
			ParsajStranicuActaCroatica(j.GetString)
		Else
			Msgbox2Async("Još nema spominjanja u izvorima!", "Info", "U redu", "", "", Null, False)
			Wait For Msgbox_Result (Result As Int)
			If Result = DialogResponse.POSITIVE Then
				PokaziDlgTraziActa
			End If
		End If
	Else
		Log("neka greška!")
	End If
		
	j.Release
	ProgressDialogHide
End Sub

Sub ParsajStranicuActaCroatica(str As String)
	pojamActaOpis = ""
'	Dim matcher1 As Matcher
'	Dim zadnjiHTML As String = $"<div class="m-t-lg block block-inverse app-footer" id="footer">"$
	Dim l1 As List
	l1.Initialize
	str = str.Replace("&hellip;", "...")
'	Dim sf As StringFunctions
'	sf.Initialize
	Dim ss As String
	Dim l As List = Regex.Split($"<div class="featured-list-icon text-primary">"$, str)
	For i = 1 To l.Size - 1
		If i = l.Size - 1 Then
'			Log("i = l.size-1: " & i & " : " & (l.Size-1))
'			ss = l.Get(i)
			ss = Regex.Replace($"<\/?\w+((\s+\w+(\s*=\s*(?:\".*?"|'.*?'|[^'\">\s]+))?)+\s*|\s*)\/?>"$, l.Get(i), "")
'			Log(ss)
			Dim ss1 As String = ss.SubString2(0, ss.IndexOf("Impresum"))
			Log(i & ":" & ss1)
			l1.Add(ss1)
		Else
			Log(i & ":" & Regex.Replace($"<\/?\w+((\s+\w+(\s*=\s*(?:\".*?"|'.*?'|[^'\">\s]+))?)+\s*|\s*)\/?>"$, l.Get(i), ""))
			l1.Add(Regex.Replace($"<\/?\w+((\s+\w+(\s*=\s*(?:\".*?"|'.*?'|[^'\">\s]+))?)+\s*|\s*)\/?>"$, l.Get(i), ""))
		End If
	Next

	For i = 0 To l1.Size - 1
		Dim s1 As String = l1.Get(i)
		s1 = s1.Trim
'		s1 = s1.Trim.Replace(" ", "")
		pojamActaOpis = pojamActaOpis & s1 & CRLF
	Next

	CallSubDelayed(eudict_servis, "UbaciActaCroatica")
	Activity.Finish
End Sub

Sub PokaziDlgTrazi
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")
	Dim x As Int = mapa.GetValueAt(0)
	
	Dim sf As Object = DetailsDialog.ShowAsync("Upit za " & Starter.puniNaziv.Get(x), "U redu", "Odbaci", "", LoadBitmap(File.DirAssets, "form.png"), True)
	DetailsDialog.SetSize(100%x, 50%y)
	Wait For (sf) Dialog_Ready(pnl As Panel)
	pnl.LoadLayout("dlg_trazi")
	'0x00002000 = TYPE_TEXT_FLAG_CAP_WORDS (capitalize first character of each word)
'	edtRijec.EditText.InputType = Bit.Or(0x00002000, edtRijec.EditText.InputType)
'	DialogLastName.EditText.InputType = Bit.Or(0x00002000, DialogLastName.EditText.InputType)
'	DialogAge.Add("")
'	For i = 1 To 120
'		DialogAge.Add(i)
'	Next
'	DetailsDialog.GetButton(DialogResponse.POSITIVE).Enabled = False
	Wait For (sf) Dialog_Result(res As Int)
	If res = DialogResponse.POSITIVE Then
'		Log(edtRijec.Text)
		PosaljiUpitEudict(edtRijec.Text)
'		ToastMessageShow($"${DialogFirstName.Text} ${DialogLastName.Text} is ${DialogAge.SelectedItem} years old"$, True)
	Else
		Activity.Finish
	End If
End Sub

Sub PosaljiUpitEudict(rijec As String)
	Dim j As HttpJob
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")
	Dim x As Int = mapa.GetValueAt(0)

	ProgressDialogShow("Šaljem upit...")
	j.Initialize("", Me)
'	Log("https://eudict.com/?lang=" & Starter.skraceniNaziv.Get(x) & "&word=" & rijec)
	j.Download("https://eudict.com/?lang=" & Starter.skraceniNaziv.Get(x) & "&word=" & rijec)
	z_eudict_link = "https://eudict.com/?lang=" & Starter.skraceniNaziv.Get(x) & "&word=" & rijec
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		If j.GetString.Contains("Word is not found.") = False Or j.GetString.Contains("WORD IS NOT FOUND.") = False Then
'			Log(j.GetString)
			ParsajStranicuEudict(j.GetString)
		Else
			Msgbox2Async("Riječ nije pronađena!", "Info", "U redu", "", "", Null, False)
			Wait For Msgbox_Result (Result As Int)
			If Result = DialogResponse.POSITIVE Then
				PokaziDlgTrazi
			End If
		End If
	Else
		Log("neka greška!")
	End If
		
	j.Release
	ProgressDialogHide
End Sub

Sub ParsajStranicuEudict(str As String)
	Dim matcher1 As Matcher
	rijecG.Clear
	prijevodG.Clear

	matcher1 = Regex.Matcher($"class='uk-table-link uk-link-reset'><a href='https://eudict.com/\?lang=.*?&amp;word=.*?'>(.*?)</a></td><td class='uk-table-link uk-link-reset'><a href='https://eudict.com/\?lang=.*?&amp;word=.*?'>(.*?)</a>"$, str)
	Do While matcher1.Find
'		Log(matcher1.Group(1))
		rijecG.Add(matcher1.Group(1))
'		Log(matcher1.Group(2))
		prijevodG.Add(matcher1.Group(2))
	Loop

	CallSubDelayed(eudict_servis, "UbaciPrijevod")
	Activity.Finish
End Sub
