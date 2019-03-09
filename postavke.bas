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
	Private spnRijecnici As Spinner
	Dim mapa As Map
	Private rbEudict As RadioButton
	Private rbHrvEnc As RadioButton
	Private rbActaCroatica As RadioButton
	Private rbStranaRijec As RadioButton
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("postavke")

	mapa.Initialize

	spnRijecnici.AddAll(Starter.puniNaziv)
	mapa = File.ReadMap(Starter.FilePath, "postavke")
'	Log(mapa)
	spnRijecnici.SelectedIndex = mapa.GetValueAt(0)
	rbEudict.Checked = mapa.Get("eudict")
	rbHrvEnc.Checked = mapa.Get("HRVenc")
	rbActaCroatica.Checked = mapa.Get("acta")
	rbStranaRijec.Checked = mapa.Get("stranaRijec")
'	If rbEudict.Checked Then
'		spnRijecnici.Enabled = True
'	Else
'		spnRijecnici.Enabled = False
'	End If
End Sub

Sub Activity_Resume
	Dim p As Phone

	p.SetScreenOrientation(1)
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	CallSubDelayed(eudict_servis, "ApdejtajStatus2")
End Sub

Sub spnRijecnici_ItemClick (Position As Int, Value As Object)
	mapa.Put("jezikKratkiNaziv", Position)
	File.WriteMap(Starter.FilePath, "postavke", mapa)
End Sub

Sub rbEudict_CheckedChange(Checked As Boolean)
	spnRijecnici.Enabled = True
	mapa.Put("eudict", Checked)
	mapa.Put("HRVenc", False)
	mapa.Put("acta", False)
	mapa.Put("stranaRijec", False)
	File.WriteMap(Starter.FilePath, "postavke", mapa)
End Sub

Sub rbHrvEnc_CheckedChange(Checked As Boolean)
	spnRijecnici.Enabled = False
	mapa.Put("HRVenc", Checked)
	mapa.Put("eudict", False)
	mapa.Put("acta", False)
	mapa.Put("stranaRijec", False)
	File.WriteMap(Starter.FilePath, "postavke", mapa)
End Sub

Sub rbActaCroatica_CheckedChange(Checked As Boolean)
	spnRijecnici.Enabled = False
	mapa.Put("acta", Checked)
	mapa.Put("eudict", False)
	mapa.Put("HRVenc", False)
	mapa.Put("stranaRijec", False)
	File.WriteMap(Starter.FilePath, "postavke", mapa)
End Sub

Sub rbStranaRijec_CheckedChange(Checked As Boolean)
	spnRijecnici.Enabled = False
	mapa.Put("stranaRijec", Checked)
	mapa.Put("HRVenc", False)
	mapa.Put("eudict", False)
	mapa.Put("acta", False)
	File.WriteMap(Starter.FilePath, "postavke", mapa)
End Sub