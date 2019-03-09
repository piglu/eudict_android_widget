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
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private DetailsDialogEudict As CustomLayoutDialog
	Private clv1 As CustomListView
	Private lblTitle1 As B4XView
	Private pnlTitle1 As B4XView
	Private ExpandedHeightEudict As Int = 240dip
	Private CollapsedHeightEudict As Int = 60dip
'	Private pnlExpanded As B4XView
	Private xui As XUI
	Private EditText1 As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")
	If mapa.Get("eudict") Then
		PokaziDlgZadnjih10Eudict
	Else If mapa.Get("HRVenc") Then
		PokaziDlgZadnjih10HRVEnc
	Else If mapa.Get("acta") Then
		PokaziDlgZadnjih10Acta
	Else
		PokaziDlgZadnjih10StranaRijec
	End If
End Sub

Sub Activity_Resume
	Dim p As Phone

	p.SetScreenOrientation(1)
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub PokaziDlgZadnjih10StranaRijec
	Log("PokaziDlgZadnjih10StranaRijec")
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	Dim sf As Object = DetailsDialogEudict.ShowAsync("Zadnjih 10 pretraživanja", "U redu", "", "", LoadBitmap(File.DirAssets, "form.png"), True)
	DetailsDialogEudict.SetSize(100%x, 80%y)
	Wait For (sf) Dialog_Ready(pnl As Panel)
	pnl.LoadLayout("dlg_zadnjih_10")
	Dim x As Int = mapa.Get("ukupnoRSR")
	x = x - 1
	clv1.Clear
	If File.Exists(Starter.FilePath, "rsr" & x) Then
		For i = 0 To x - 1
			Dim text As String = File.ReadString(Starter.FilePath, "rsr" & x)
			Dim s As String = text.SubString2(0, text.IndexOf(";"))
			s = text.SubString2(text.IndexOf(";")+1, text.Length)'.Trim
			clv1.Add(CreateItemEudict(s, text), "")
		Next
		Wait For (sf) Dialog_Result(res As Int)
		If res = DialogResponse.POSITIVE Then
			Activity.Finish
		End If
	Else
		Msgbox2Async("Ništa za prikazati!!", "Info", "U redu", "", "", Null, False)
		Wait For Msgbox_Result (Result As Int)
		If Result = DialogResponse.POSITIVE Then
			Activity.Finish
		End If
	End If
End Sub

Sub PokaziDlgZadnjih10HRVEnc
	Log("PokaziDlgZadnjih10HRVEnc")
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	Dim sf As Object = DetailsDialogEudict.ShowAsync("Zadnjih 10 pretraživanja", "U redu", "", "", LoadBitmap(File.DirAssets, "form.png"), True)
	DetailsDialogEudict.SetSize(100%x, 80%y)
	Wait For (sf) Dialog_Ready(pnl As Panel)
	pnl.LoadLayout("dlg_zadnjih_10")
	Dim x As Int = mapa.Get("ukupnoHE")
	x = x - 1
	clv1.Clear
	If File.Exists(Starter.FilePath, "he" & x) Then
		For i = 0 To x - 1
			Dim text As String = File.ReadString(Starter.FilePath, "he" & x)
			Dim s As String = text.SubString2(0, text.IndexOf(";"))
			s = text.SubString2(text.IndexOf(";")+1, text.Length)
			clv1.Add(CreateItemEudict(s, text), "")
		Next
		Wait For (sf) Dialog_Result(res As Int)
		If res = DialogResponse.POSITIVE Then
			Activity.Finish
		End If
	Else
		Msgbox2Async("Ništa za prikazati!!", "Info", "U redu", "", "", Null, False)
		Wait For Msgbox_Result (Result As Int)
		If Result = DialogResponse.POSITIVE Then
			Activity.Finish
		End If
	End If
End Sub

Sub PokaziDlgZadnjih10Acta
	Log("PokaziDlgZadnjih10Acta")
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	Dim sf As Object = DetailsDialogEudict.ShowAsync("Zadnjih 10 pretraživanja", "U redu", "", "", LoadBitmap(File.DirAssets, "form.png"), True)
	DetailsDialogEudict.SetSize(100%x, 80%y)
	Wait For (sf) Dialog_Ready(pnl As Panel)
	pnl.LoadLayout("dlg_zadnjih_10")
	Dim x As Int = mapa.Get("ukupnoAC")
	x = x - 1
	clv1.Clear
	If File.Exists(Starter.FilePath, "ac" & x) Then
		For i = 0 To x - 1
			Dim text As String = File.ReadString(Starter.FilePath, "ac" & x)
			Dim s As String = text.SubString2(0, text.IndexOf(";"))
			s = text.SubString2(text.IndexOf(";")+1, text.Length)
			clv1.Add(CreateItemEudict(s, text), "")
		Next
		Wait For (sf) Dialog_Result(res As Int)
		If res = DialogResponse.POSITIVE Then
			Activity.Finish
		End If
	Else
		Msgbox2Async("Ništa za prikazati!!", "Info", "U redu", "", "", Null, False)
		Wait For Msgbox_Result (Result As Int)
		If Result = DialogResponse.POSITIVE Then
			Activity.Finish
		End If
	End If
End Sub

Sub PokaziDlgZadnjih10Eudict
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	Dim sf As Object = DetailsDialogEudict.ShowAsync("Zadnjih 10 pretraživanja", "U redu", "", "", LoadBitmap(File.DirAssets, "form.png"), True)
	DetailsDialogEudict.SetSize(100%x, 80%y)
	Wait For (sf) Dialog_Ready(pnl As Panel)
	pnl.LoadLayout("dlg_zadnjih_10")
	Dim x As Int = mapa.Get("ukupnoP")
	x = x - 1
	clv1.Clear
	If File.Exists(Starter.FilePath, "p" & x) Then
		For i = 0 To x - 1
			Dim text As String = File.ReadString(Starter.FilePath, "p" & (i+1))
			Log("text: " & text)
			Dim s As String = text.SubString2(0, text.IndexOf(" "))
			Log("s: " & s)
			clv1.Add(CreateItemEudict(s, text), "")
		Next
		Wait For (sf) Dialog_Result(res As Int)
		If res = DialogResponse.POSITIVE Then
			Activity.Finish
		End If
	Else
		Msgbox2Async("Ništa za prikazati!!", "Info", "U redu", "", "", Null, False)
		Wait For Msgbox_Result (Result As Int)
		If Result = DialogResponse.POSITIVE Then
			Activity.Finish
		End If
	End If
End Sub


Sub CreateItemEudict(Title As String, txt As String) As B4XView
	Dim p As B4XView = xui.CreatePanel("")
	p.SetLayoutAnimated(0, 0, 0, clv1.AsView.Width, ExpandedHeightEudict)
	p.LoadLayout("stavka_zadnjih_10")
	p.SetLayoutAnimated(0, 0, 0, p.Width, CollapsedHeightEudict)
	lblTitle1.Text = Title
	EditText1.Text = txt
'	pnlTitle.Color = clr
'	pnlExpanded.Color = ShadeColor(clr)
	p.Tag = False 'collapsed

	Return p
End Sub

Sub ExpandItemEudict(index As Int)
	clv1.ResizeItem(index, ExpandedHeightEudict)
	clv1.GetPanel(index).Tag = True
	AnimatedArrowEudict(index, 0, 180)
End Sub

Sub AnimatedArrowEudict(index As Int, From As Int, ToDegree As Int)
	pnlTitle1 = clv1.GetPanel(index).GetView(0) 'pnlTitle is the first item
	Dim iv As B4XView = pnlTitle1.GetView(1) 'ImageView1 is the second item
	iv.SetRotationAnimated(0, From)
	iv.SetRotationAnimated(clv1.AnimationDuration, ToDegree)
End Sub

Sub CollapseItemEudict(index As Int)
	clv1.ResizeItem(index, CollapsedHeightEudict)
	clv1.GetPanel(index).Tag = False
	AnimatedArrowEudict(index, 180, 0)
End Sub

Sub clv1_ItemClick (Index As Int, Value As Object)
	Dim p As B4XView = clv1.GetPanel(Index)
	If p.Tag = True Then
		CollapseItemEudict(Index)
	Else
		ExpandItemEudict(Index)
	End If
End Sub