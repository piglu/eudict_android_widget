B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=7.3
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim rv As RemoteViews
End Sub

Sub Service_Create
	rv = ConfigureHomeWidget("eudict", "rv", 30, "Riječnici", False)
End Sub

Sub Service_Start (StartingIntent As Intent)
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	If mapa.Get("eudict") Then
		PrikaziZadnjiPrijevodUWidgetu
	else if mapa.Get("HRVenc") Then
		PrikaziZadnjeIzHRVEnciklopedije
	Else if mapa.Get("acta") Then
		PrikaziZadnjeActaCroatica
	Else
		PrikaziZadnjuStranuRijec
	End If

	rv.HandleWidgetEvents(StartingIntent)
	Sleep(0)
	Service.StopAutomaticForeground
End Sub

Sub Service_Destroy

End Sub

Sub rv_RequestUpdate
	rv.UpdateWidget
End Sub

Sub rv_Disabled
	StopService("")
End Sub

Sub imgLink_Click
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")
	Dim s As String
	Dim p As PhoneIntents

	If mapa.Get("eudict") Then
		s = File.ReadString(Starter.FilePath, "ze_link")
	else if mapa.Get("HRVenc") Then
		s = File.ReadString(Starter.FilePath, "zenc_link")
	Else if mapa.Get("acta") Then
		s = File.ReadString(Starter.FilePath, "za_link")
	Else
		s = File.ReadString(Starter.FilePath, "zsr_link")
	End If

	StartActivity (p.OpenBrowser(s))
End Sub

Sub btnActa_Click
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	mapa.Put("acta", True)
	mapa.Put("eudict", False)
	mapa.Put("HRVenc", False)
	mapa.Put("stranaRijec", False)
	File.WriteMap(Starter.FilePath, "postavke", mapa)

	ApdejtajStatus2
End Sub

Sub btnEudict_Click
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	mapa.Put("eudict", True)
	mapa.Put("HRVenc", False)
	mapa.Put("acta", False)
	mapa.Put("stranaRijec", False)
	File.WriteMap(Starter.FilePath, "postavke", mapa)

	ApdejtajStatus2
End Sub

Sub btnHrvEnc_Click
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	mapa.Put("HRVenc", True)
	mapa.Put("eudict", False)
	mapa.Put("acta", False)
	mapa.Put("stranaRijec", False)
	File.WriteMap(Starter.FilePath, "postavke", mapa)

	ApdejtajStatus2
End Sub

Sub btnStraneRijeci_Click
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	mapa.Put("stranaRijec", True)
	mapa.Put("HRVenc", False)
	mapa.Put("eudict", False)
	mapa.Put("acta", False)
	File.WriteMap(Starter.FilePath, "postavke", mapa)

	ApdejtajStatus2
End Sub

Sub imgTrazi_Click
'	Log("traži prijevod riječi!")
	StartActivity(dlgTrazi)
End Sub

Sub imgZadnjiZapisi_Click
'	Log("zadnjih 10 zapisa!")
	StartActivity(zadnji)
End Sub

Sub imgOpcije_Click
'	Log("opcije!")
	StartActivity(postavke)
End Sub

Sub PrikaziZadnjiPrijevodUWidgetu
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	Dim x As Int = mapa.Get("ukupnoP")
	x = x - 1
	If File.Exists(Starter.FilePath, "p" & x) Then
		Dim text As String = File.ReadString(Starter.FilePath, "p" & x)
		Dim s As String = text.SubString2(0, text.IndexOf(" "))
		rv.SetText("lblTrazena", s)
		rv.SetText("lblPrijevod", text)
	Else
		rv.SetText("lblTrazena", "Nema tražene riječi!")
		rv.SetText("lblPrijevod", "Nema tražene riječi!")
	End If

	ApdejtajStatus1
End Sub

Sub UbaciPrijevod
	Dim s As String
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	If dlgTrazi.rijecG.Size > 0 Then
	rv.SetText("lblTrazena", dlgTrazi.rijecG.Get(0))
	For i = 0 To dlgTrazi.prijevodG.Size - 1
		s = s & dlgTrazi.rijecG.Get(i) & " → " & dlgTrazi.prijevodG.Get(i) & CRLF
	Next
	If dlgTrazi.prijevodG.Size > 0 Then
		Dim x As Int = mapa.Get("ukupnoP")
		File.WriteString(Starter.FilePath, "p" & x, s)
		x = x + 1
		If x = 10 Then x = 1
		mapa.Put("ukupnoP", x)
		File.WriteMap(Starter.FilePath, "postavke", mapa)
	End If

	File.writestring(Starter.FilePath, "ze_link", dlgTrazi.z_eudict_link)

	rv.SetText("lblPrijevod", s)

	ApdejtajStatus1

	rv_RequestUpdate
	End If
End Sub

Sub PrikaziZadnjeIzHRVEnciklopedije
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	Dim x As Int = mapa.Get("ukupnoHE")
	x = x - 1
	If File.Exists(Starter.FilePath, "he" & x) Then
		Dim text As String = File.ReadString(Starter.FilePath, "he" & x)
		Dim s As String = text.SubString2(0, text.IndexOf(";"))
		rv.SetText("lblTrazena", s)
		s = text.SubString2(text.IndexOf(";")+1, text.Length)
		rv.SetText("lblPrijevod", s)
	Else
		rv.SetText("lblTrazena", "Nema traženog pojma!")
		rv.SetText("lblPrijevod", "Nema traženog pojma!")
	End If

	ApdejtajStatus1
End Sub

Sub UbaciHRVencPojam
	rv.SetText("lblTrazena", dlgTrazi.pojamHRVenc)
	rv.SetText("lblPrijevod", dlgTrazi.pojamHRVencTekst)

	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")
	Dim x As Int = mapa.Get("ukupnoHE")
	File.WriteString(Starter.FilePath, "he" & x, dlgTrazi.pojamHRVenc & ";" & dlgTrazi.pojamHRVencTekst)
	x = x + 1
	If x = 10 Then x = 1
	mapa.Put("ukupnoHE", x)
	File.WriteMap(Starter.FilePath, "postavke", mapa)
	File.WriteString(Starter.FilePath, "zenc_link", dlgTrazi.hrv_enc_link)

	ApdejtajStatus1

	rv_RequestUpdate
End Sub

Sub PrikaziZadnjeActaCroatica
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	Dim x As Int = mapa.Get("ukupnoAC")
	x = x - 1
	If File.Exists(Starter.FilePath, "ac" & x) Then
		Dim text As String = File.ReadString(Starter.FilePath, "ac" & x)
		Dim s As String = text.SubString2(0, text.IndexOf(";"))
		rv.SetText("lblTrazena", s)
		s = text.SubString2(text.IndexOf(";")+1, text.Length)
		rv.SetText("lblPrijevod", s)
	Else
		rv.SetText("lblTrazena", "Nema traženog pojma!")
		rv.SetText("lblPrijevod", "Nema traženog pojma!")
	End If

	ApdejtajStatus1
End Sub

Sub UbaciActaCroatica
	rv.SetText("lblTrazena", dlgTrazi.pojamActa)
	rv.SetText("lblPrijevod", dlgTrazi.pojamActaOpis)

	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")
	Dim x As Int = mapa.Get("ukupnoAC")
	File.WriteString(Starter.FilePath, "ac" & x, dlgTrazi.pojamActa & ";" & dlgTrazi.pojamActaOpis)
	x = x + 1
	If x = 10 Then x = 1
	mapa.Put("ukupnoAC", x)
	File.WriteMap(Starter.FilePath, "postavke", mapa)
	File.WriteString(Starter.FilePath, "za_link", dlgTrazi.z_acta_link)

	ApdejtajStatus1

	rv_RequestUpdate
End Sub

Sub PrikaziZadnjuStranuRijec
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	Dim x As Int = mapa.Get("ukupnoRSR")
	x = x - 1
	If File.Exists(Starter.FilePath, "rsr" & x) Then
		Dim text As String = File.ReadString(Starter.FilePath, "rsr" & x)
		Dim s As String = text.SubString2(0, text.IndexOf(";"))
		rv.SetText("lblTrazena", s)
		s = text.SubString2(text.IndexOf(";")+1, text.Length)
		rv.SetText("lblPrijevod", s)
	Else
		rv.SetText("lblTrazena", "Nema traženog pojma!")
		rv.SetText("lblPrijevod", "Nema traženog pojma!")
	End If

	ApdejtajStatus1
End Sub

Sub UbaciStranuRijec
	rv.SetText("lblTrazena", dlgTrazi.stranaRijec)
	rv.SetText("lblPrijevod", dlgTrazi.stranaRijecOpis)

	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")
	Dim x As Int = mapa.Get("ukupnoRSR")
	File.WriteString(Starter.FilePath, "rsr" & x, dlgTrazi.stranaRijec & ";" & dlgTrazi.stranaRijecOpis)
	x = x + 1
	If x = 10 Then x = 1
	mapa.Put("ukupnoRSR", x)
	File.WriteMap(Starter.FilePath, "postavke", mapa)
	File.WriteString(Starter.FilePath, "zsr_link", dlgTrazi.strana_rijec_link)

	ApdejtajStatus1

	rv_RequestUpdate
End Sub

Sub ApdejtajStatus2
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	If mapa.Get("eudict") Then
		PrikaziZadnjiPrijevodUWidgetu
	else if mapa.Get("HRVenc") Then
		PrikaziZadnjeIzHRVEnciklopedije
	Else if mapa.Get("acta") Then
		PrikaziZadnjeActaCroatica
	Else
		PrikaziZadnjuStranuRijec
	End If

	ApdejtajStatus1
End Sub

Sub ApdejtajStatus1
	Dim mapa As Map = File.ReadMap(Starter.FilePath, "postavke")

	If mapa.Get("eudict") Then
		rv.SetText("lblStatus", "Eudict riječnici")
	else if mapa.Get("HRVenc") Then
		rv.SetText("lblStatus", "Hrvatska enciklopedija")
	Else if mapa.Get("acta") Then
		rv.SetText("lblStatus", "Acta Croatica")
	Else
		rv.SetText("lblStatus", "Hrvatski jezični portal")
	End If

	rv.UpdateWidget
End Sub
