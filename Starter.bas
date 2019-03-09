B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Service
Version=7.3
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Private mtch1 As String = $"<option value='(.*?)'>(.*?)</option>"$
	Dim skraceniNaziv, puniNaziv As List
	Dim mapa As Map
	Dim ukupnoP As Int = 1
	Dim ukupnoHE As Int = 1
	Dim ukupnoAC As Int = 1
	Dim ukupnoRSR As Int = 1
	Public rp As RuntimePermissions
	Public FilePath As String
End Sub

Sub Service_Create
	'This is the program entry point.
	'This is a good place to load resources that are not specific to a single activity.
End Sub

Sub Service_Start (StartingIntent As Intent)
	Dim m As Map

	FilePath = rp.GetSafeDirDefaultExternal("")

	skraceniNaziv.Initialize
	puniNaziv.Initialize
	m.Initialize
	mapa.Initialize

	Dim matcher1 As Matcher
	Dim str As String = File.ReadString(File.DirAssets, "tekst.txt")
	matcher1 = Regex.Matcher(mtch1, str)
	Do While matcher1.Find
		m.Put(matcher1.Group(1), matcher1.Group(2))
		skraceniNaziv.Add(matcher1.Group(1))
		puniNaziv.Add(matcher1.Group(2))
	Loop

'	File.Delete(FilePath, "postavke")
	If File.Exists(FilePath, "postavke") = False Then
		mapa.Put("jezikKratkiNaziv", skraceniNaziv.IndexOf("croeng"))
		mapa.Put("ukupnoP", ukupnoP)
		mapa.Put("ukupnoHE", ukupnoHE)
		mapa.Put("ukupnoAC", ukupnoAC)
		mapa.Put("ukupnoRSR", ukupnoRSR)
		mapa.Put("eudict", True)
		mapa.Put("HRVenc", False)
		mapa.Put("acta", False)
		mapa.Put("stranaRijec", False)
		File.WriteMap(FilePath, "postavke", mapa)
	End If

End Sub

Sub Service_TaskRemoved
	'This event will be raised when the user removes the app from the recent apps list.
End Sub

'Return true to allow the OS default exceptions handler to handle the uncaught exception.
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy

End Sub
