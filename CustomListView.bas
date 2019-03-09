B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=7.3
@EndOfDesignText@
'xCustomListView v1.02 - modified version (Expandable items)
#Event: ItemClick (Index As Int, Value As Object)
#Event: ReachEnd
#DesignerProperty: Key: DividerColor, DisplayName: Divider Color, FieldType: Color, DefaultValue: 0xFFD9D7DE
#DesignerProperty: Key: DividerHeight, DisplayName: Divider Height, FieldType: Int, DefaultValue: 2
#DesignerProperty: Key: PressedColor, DisplayName: Pressed Color, FieldType: Color, DefaultValue: 0xFF7EB4FA
#DesignerProperty: Key: InsertAnimationDuration, DisplayName: Insert Animation Duration, FieldType: Int, DefaultValue: 300
Sub Class_Globals
	Private mBase As B4XView
	Public sv As B4XView
	Type CLVItem(Panel As B4XView, Height As Int, Value As Object, Color As Int, TextItem As Boolean)
	Private items As List
	Private dividerHeight As Float
	Private EventName As String
	Private CallBack As Object
	Public DefaultTextColor As Int
	Public DefaultTextBackgroundColor As Int
	Public AnimationDuration As Int = 300
	Private LastReachEndEvent As Long
	Private PressedColor As Int
	Private xui As XUI
	Private LastAnimatedAction As Int
	Private DesignerLabel As Label
#if B4J
	Private fx As JFX
#else if B4A
	Private su As StringUtils
#else if B4i

#end if
End Sub

Public Sub Initialize (vCallBack As Object, vEventName As String)
	EventName = vEventName
	CallBack = vCallBack
	sv = CreateScrollView
	items.Initialize
	DefaultTextBackgroundColor = xui.Color_White
	DefaultTextColor = xui.Color_Black
End Sub

Public Sub DesignerCreateView (Base As Object, Lbl As Label, Props As Map)
	mBase = Base
	mBase.AddView(sv, 0, 0, mBase.Width, mBase.Height)
	sv.ScrollViewInnerPanel.Color = xui.PaintOrColorToColor(Props.Get("DividerColor"))
	dividerHeight = DipToCurrent(Props.Get("DividerHeight")) 'need to scale the value
	PressedColor = xui.PaintOrColorToColor(Props.Get("PressedColor"))
	AnimationDuration = Props.GetDefault("InsertAnimationDuration", AnimationDuration)
	DefaultTextColor = xui.PaintOrColorToColor(Lbl.TextColor)
	DefaultTextBackgroundColor = mBase.Color
	DesignerLabel = Lbl
	Base_Resize(mBase.Width, mBase.Height)
End Sub

Private Sub Base_Resize (Width As Double, Height As Double)
	sv.SetLayoutAnimated(0, 0, 0, Width, Height)
	sv.ScrollViewContentWidth = Width
#if B4J
	sv.ScrollViewContentWidth = Width - 20 'vertical scrollbar
#End If
	For Each it As CLVItem In items
		it.Panel.Width = sv.ScrollViewContentWidth
		it.Panel.GetView(0).Width = it.Panel.Width
		If it.TextItem Then
			Dim lbl As B4XView = it.Panel.GetView(0).GetView(0)
			lbl.SetLayoutAnimated(0, lbl.Left, lbl.Top, it.Panel.Width - lbl.Left, lbl.Height)
		End If
	Next
End Sub

Public Sub AsView As B4XView
	Return mBase
End Sub

'Clears all items.
Public Sub Clear
	items.Clear
	sv.ScrollViewInnerPanel.RemoveAllViews
	sv.ScrollViewContentHeight = 0
End Sub

Public Sub GetBase As B4XView
	Return mBase
End Sub

'Returns the number of items.
Public Sub getSize As Int
	Return items.Size
End Sub

Private Sub GetItem(Index As Int) As CLVItem
	Return items.Get(Index)
End Sub

'Returns the Panel stored at the specified index.
Public Sub GetPanel(Index As Int) As B4XView
	Return GetItem(Index).Panel.GetView(0)
End Sub

'Returns the value stored at the specified index.
Public Sub GetValue(Index As Int) As Object
	Return GetItem(Index).Value
End Sub

'Removes the item at the specified index.
Public Sub RemoveAt(Index As Int)
	If getSize <= 1 Then
		Clear
		Return
	End If
	Dim RemoveItem As CLVItem = items.Get(Index)
	Dim p As B4XView
	For i = Index + 1 To items.Size - 1
		Dim item As CLVItem = items.Get(i)
		p = item.Panel
		p.Tag = i - 1
		Dim NewTop As Int = p.Top - item.Height - dividerHeight
		p.top = NewTop
	Next
	sv.ScrollViewContentHeight = sv.ScrollViewContentHeight - RemoveItem.Height - dividerHeight
	items.RemoveAt(Index)
	RemoveItem.Panel.RemoveViewFromParent
End Sub


'Adds a text item. The item height will be adjusted based on the text.
Public Sub AddTextItem(Text As Object, Value As Object)
	InsertAtTextItem(items.Size, Text, Value)
End Sub

'Inserts a text item at the specified index.
Public Sub InsertAtTextItem(Index As Int, Text As Object, Value As Object)
	Dim pnl As B4XView = CreatePanel("")
	Dim lbl As B4XView = CreateLabel(Text)
	lbl.Height = Max(50dip, lbl.Height)
	pnl.AddView(lbl, 5dip, 2dip, sv.ScrollViewContentWidth - 10dip, lbl.Height)
	lbl.TextColor = DefaultTextColor
	pnl.Color = DefaultTextBackgroundColor
	pnl.Height = lbl.Height + 2dip
	InsertAt(Index, pnl, Value)
	Dim item As CLVItem = GetItem(Index)
	item.TextItem = True
End Sub

'Adds a custom item at the specified index.
Public Sub InsertAt(Index As Int, pnl As B4XView, Value As Object)
	InsertAtImpl(Index, pnl, pnl.Height, Value, 0)
End Sub

	
Private Sub InsertAtImpl(Index As Int, Pnl As B4XView, ItemHeight As Int, Value As Object, InitialHeight As Int)
	'create another panel to handle the click event
	Dim p As B4XView = CreatePanel("Panel")
	p.Color = Pnl.Color
	Pnl.Color = xui.Color_Transparent
	p.AddView(Pnl, 0, 0, sv.ScrollViewContentWidth, ItemHeight)
	p.Tag = Index
	Dim IncludeDividierHeight As Int
	If InitialHeight = 0 Then IncludeDividierHeight = dividerHeight Else IncludeDividierHeight = 0
	Dim NewItem As CLVItem
	NewItem.Panel = p
	NewItem.Height = ItemHeight
	NewItem.Value = Value
	NewItem.Color = p.Color
	If Index = items.Size And InitialHeight = 0 Then
		items.Add(NewItem)
		Dim top As Int
		If Index = 0 Then top = dividerHeight Else top = sv.ScrollViewInnerPanel.Height
		sv.ScrollViewInnerPanel.AddView(p, 0, top, sv.Width, ItemHeight)
	Else
		CleanAfterAnimation
		Dim top As Int
		If Index = 0 Then
			top = dividerHeight
		Else
			Dim PrevItem As CLVItem = items.Get(Index - 1)
			top = PrevItem.Panel.top + PrevItem.Height + dividerHeight
		End If
		For i = Index To items.Size - 1
			Dim it As CLVItem = items.Get(i)
			Dim NewTop As Int = it.Panel.top + ItemHeight - InitialHeight + IncludeDividierHeight
			If  Min(NewTop, it.Panel.Top) - sv.ScrollViewOffsetY < sv.Height Then
				it.Panel.SetLayoutAnimated(AnimationDuration, 0, NewTop, it.Panel.Width, it.Height)
			Else
				it.Panel.top = NewTop
			End If
			it.Panel.Tag = i + 1
		Next
		items.InsertAt(Index, NewItem)
		sv.ScrollViewInnerPanel.AddView(p, 0, top, sv.Width, InitialHeight)
		'***********************************
		Dim ShouldSetPanelHeight As Boolean = InitialHeight > ItemHeight
		If ShouldSetPanelHeight = True Then 'collapse
			Pnl.Height = InitialHeight
		Else
			p.SetLayoutAnimated(0, 0, top, sv.Width, ItemHeight)			
		End If
		p.SendToBack
		'****************************************************************		
	End If
	sv.ScrollViewContentHeight = sv.ScrollViewContentHeight + ItemHeight - InitialHeight + IncludeDividierHeight
	If items.Size = 1 Then sv.ScrollViewContentHeight = sv.ScrollViewContentHeight + IncludeDividierHeight
	'****************************************************
	If ShouldSetPanelHeight Then
		Sleep(AnimationDuration)
		If p.Parent.IsInitialized Then
			'this can happen if the user clicks on the item while it is being animated
			p.Height = ItemHeight
			Pnl.Height = ItemHeight
		End If
	End If
End Sub

Private Sub CleanAfterAnimation
	LastAnimatedAction = LastAnimatedAction + 1
	Dim MyIndex As Int = LastAnimatedAction
	Sleep(AnimationDuration + 50)
	If MyIndex <> LastAnimatedAction Then Return
	Dim Top As Int = dividerHeight
	For Each item As CLVItem In items
		item.Panel.Top = Top
		Top = Top + item.Height + dividerHeight
	Next
End Sub


'Changes the height of an existing item.
Public Sub ResizeItem(Index As Int, ItemHeight As Int)
	Dim p As B4XView = GetPanel(Index)
	Dim value As Object = items.Get(Index)
	Dim parent As B4XView = p.Parent
	p.Color = parent.Color
	p.RemoveViewFromParent
	ReplaceAt(Index, p, ItemHeight, value)
End Sub


'Replaces the item at the specified index with a new item.
Public Sub ReplaceAt(Index As Int, pnl As B4XView, ItemHeight As Int, Value As Object)
	Dim RemoveItem As CLVItem = items.Get(Index)
	items.RemoveAt(Index)
	RemoveItem.Panel.RemoveViewFromParent
	InsertAtImpl(Index, pnl, ItemHeight, Value, RemoveItem.Height)
End Sub



'Adds a custom item.
Public Sub Add(Pnl As B4XView, Value As Object)
	InsertAt(items.Size, Pnl, Value)
End Sub

'Scrolls the list to the specified item (without animating the scroll).
Public Sub JumpToItem(Index As Int)
	sv.ScrollViewOffsetY = FindItemTop(Index)
End Sub

'Smoothly scrolls the list to the specified item.
Public Sub ScrollToItem(Index As Int)
	#if B4J
	JumpToItem(Index)
	#Else If B4A
	Dim nsv As ScrollView = sv
	nsv.ScrollPosition = FindItemTop(Index) 'smooth scroll
	#End If
End Sub

Private Sub FindItemTop(Index As Int) As Int
	Dim top As Int
	For i = 0 To Min(Index - 1, items.Size - 1)
		top = top + GetItem(i).Height + dividerHeight
	Next
	Return top
End Sub


'Gets the index of the first visible item.
Public Sub getFirstVisibleIndex As Int
	Dim bottom As Int
	For i = 0 To items.Size - 1
		bottom = bottom +  GetItem(i).Height + dividerHeight
		If bottom > sv.ScrollViewOffsetY Then Return i
	Next
	Return items.Size - 1
End Sub

'Gets the index of the last visible item.
Public Sub getLastVisibleIndex As Int
	Dim first As Int = getFirstVisibleIndex
	Dim bottom As Int
	For i = 0 To items.Size - 1
		bottom = bottom +  GetItem(i).Height + dividerHeight
		If i >= first And bottom >= sv.ScrollViewOffsetY  + sv.Height Then Return Max(i - 1, first)
	Next
	Return items.Size - 1
End Sub

Private Sub PanelClickHandler(SenderPanel As B4XView)
	Dim clr As Int = GetItem(SenderPanel.Tag).Color
	SenderPanel.SetColorAnimated(50, clr, PressedColor)
	If xui.SubExists(CallBack, EventName & "_ItemClick", 2) Then
		CallSub3(CallBack, EventName & "_ItemClick", SenderPanel.Tag, GetItem(SenderPanel.Tag).Value)
	End If
	Sleep(200)
	SenderPanel.SetColorAnimated(200, PressedColor, clr)
End Sub

'Returns the index of the item that holds the given view.
Public Sub GetItemFromView(v As B4XView) As Int
	Dim parent = v As Object, current As B4XView
	Do While sv.ScrollViewInnerPanel <> parent
		current = parent
		parent = current.Parent
	Loop
	v = current
	Return v.Tag
End Sub

Private Sub ScrollHandler
	Dim position As Int = sv.ScrollViewOffsetY
	If position + sv.Height >= sv.ScrollViewContentHeight - 5dip And DateTime.Now > LastReachEndEvent + 100 Then
		If xui.SubExists(CallBack, EventName & "_ReachEnd", 0) Then
			LastReachEndEvent = DateTime.Now
			CallSubDelayed(CallBack, EventName & "_ReachEnd")
		Else
			LastReachEndEvent = DateTime.Now + 1000 * DateTime.TicksPerDay 'disable
		End If
	End If
End Sub

Private Sub CreatePanel (PanelEventName As String) As B4XView
	Return xui.CreatePanel(PanelEventName)
End Sub


'******************************* platform specific ***************************************************

#If B4A

Private Sub CreateScrollView As B4XView
	Dim nsv As ScrollView
	nsv.Initialize2(0, "sv")
	Return nsv
End Sub

Private Sub Panel_Click
	PanelClickHandler(Sender)
End Sub

Private Sub sv_ScrollChanged(Position As Int)
	ScrollHandler
End Sub


Private Sub CreateLabel(txt As String) As B4XView
	Dim lbl As Label
	lbl.Initialize("")
	lbl.Gravity = DesignerLabel.Gravity
	lbl.TextSize = DesignerLabel.TextSize
	lbl.SingleLine = False
	lbl.Text = txt
	lbl.Width = sv.ScrollViewContentWidth - 10dip
	lbl.Height = su.MeasureMultilineTextHeight(lbl, txt)
	Return lbl
End Sub

#else If B4i

Private Sub CreateScrollView As B4XView
	Dim nsv As ScrollView
	nsv.Initialize("sv", 0, 0)
	Return nsv
End Sub

Private Sub Panel_Click
	PanelClickHandler(Sender)
End Sub

Sub sv_ScrollChanged (OffsetX As Int, OffsetY As Int)
	ScrollHandler
End Sub

Private Sub CreateLabel(txt As String) As B4XView
	Dim lbl As Label
	lbl.Initialize("")
	lbl.TextAlignment = DesignerLabel.TextAlignment
	lbl.Font = DesignerLabel.Font
	lbl.Multiline = True
	lbl.Text = txt
	lbl.Width = sv.ScrollViewContentWidth - 10dip
	lbl.SizeToFit
	Log(lbl.Height)
	Return lbl
End Sub

#else If B4J
Private Sub CreateScrollView As B4XView
	Dim nsv As ScrollPane
	nsv.Initialize("sv")
	nsv.SetHScrollVisibility("NEVER")
	Return nsv
End Sub

Private Sub Panel_MouseClicked (EventData As MouseEvent)
	PanelClickHandler(Sender)
End Sub

Private Sub sv_VScrollChanged (Position As Double)
	ScrollHandler
End Sub


Private Sub CreateLabel(txt As String) As B4XView
	Dim lbl As Label
	lbl.Initialize("")
	lbl.Alignment = DesignerLabel.Alignment
	lbl.Style = DesignerLabel.Style
	lbl.Font = DesignerLabel.Font
	lbl.WrapText = True
	lbl.Text = txt
	Dim jo As JavaObject = Me
	Dim width As Double = sv.ScrollViewContentWidth - 10dip
	lbl.PrefHeight = 20dip + jo.RunMethod("MeasureMultilineTextHeight", Array(lbl.Font, txt, width))
	Return lbl
End Sub


#if Java

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.scene.text.Font;
import javafx.scene.text.TextBoundsType;
public double MeasureMultilineTextHeight(Font f, String text, double width) throws Exception {
		Method m = Class.forName("com.sun.javafx.scene.control.skin.Utils").getDeclaredMethod("computeTextHeight",
				Font.class, String.class, double.class, TextBoundsType.class);
		m.setAccessible(true);
		return (Double)m.invoke(null, f, text, width, TextBoundsType.LOGICAL);
	}
#End If

#End If