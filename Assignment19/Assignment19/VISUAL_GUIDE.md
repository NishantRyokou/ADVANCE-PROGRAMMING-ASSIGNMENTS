# Visual Guide - Assignment 19

## App Layout

### Light Mode
```
┌─────────────────────────────────────┐
│  🌙 Dark                            |  ← Theme Toggle Button
│                                     │
│                                     │
│         Counter Value               │
│              42                     │  ← Counter Display (72px)
│                                     │
│                                     │
│  ┌──────────────┐  ┌──────────────┐ │
│  │  Decrement   │  │  Increment   │ │  ← Button Row
│  └──────────────┘  └──────────────┘ │
│                                     │
│  ┌─────────────────────────────────┐│
│  │    Reset Counter                ││  ← Reset Button
│  └─────────────────────────────────┘│
│                                     │
│  Theme: Light | Count: 42           │  ← Info Text
└─────────────────────────────────────┘
```

**Colors:**
- Background: White (#FFFFFF)
- Text: Black (#000000)
- Buttons: Blue (#007AFF)
- Button Text: White (#FFFFFF)

---

### Dark Mode
```
┌─────────────────────────────────────┐
│  ☀️ Light                           │  ← Theme Toggle Button
│                                     │
│                                     │
│         Counter Value               │
│              42                     │  ← Counter Display (72px)
│                                     │
│                                     │
│  ┌──────────────┐  ┌──────────────┐│
│  │  Decrement   │  │  Increment   ││  ← Button Row
│  └──────────────┘  └──────────────┘│
│                                     │
│  ┌─────────────────────────────────┐│
│  │    Reset Counter                ││  ← Reset Button
│  └─────────────────────────────────┘│
│                                     │
│  Theme: Dark | Count: 42            │  ← Info Text
└─────────────────────────────────────┘
```

**Colors:**
- Background: Dark Gray (#1A1A1A)
- Text: White (#FFFFFF)
- Buttons: Light Blue (#0A84FF)
- Button Text: White (#FFFFFF)

---

## Component Hierarchy

```
App Component
│
├── Theme Toggle Button
│   └── onPress: toggleTheme()
│
├── Counter Display Section
│   ├── Label: "Counter Value"
│   └── Display: {count}
│
├── Button Row (Flexbox)
│   ├── Decrement Button
│   │   └── onPress: handleDecrement()
│   └── Increment Button
│       └── onPress: handleIncrement()
│
├── Reset Button
│   └── onPress: handleReset()
│
└── Info Text
    └── "Theme: {isDarkMode ? 'Dark' : 'Light'} | Count: {count}"
```

---

## State Flow Diagram

```
Initial State
├── count = 0
└── isDarkMode = false (Light Mode)

User Interactions
├── Press Increment
│   └── count = count + 1
│
├── Press Decrement
│   ├── if (count > 0)
│   │   └── count = count - 1
│   └── else
│       └── count stays 0
│
├── Press Reset
│   └── count = 0
│
└── Press Theme Button
    └── isDarkMode = !isDarkMode

Re-render
├── Update counter display
├── Update all colors
└── Update info text
```

---

## Color Palette

### Light Mode
| Element | Color | Hex Code |
|---------|-------|----------|
| Background | White | #FFFFFF |
| Text | Black | #000000 |
| Primary Button | Blue | #007AFF |
| Secondary Button | Light Gray | #E8E8E8 |
| Button Text | White | #FFFFFF |
| Text Opacity | 100% | 1.0 |

### Dark Mode
| Element | Color | Hex Code |
|---------|-------|----------|
| Background | Dark Gray | #1A1A1A |
| Text | White | #FFFFFF |
| Primary Button | Light Blue | #0A84FF |
| Secondary Button | Dark Gray | #333333 |
| Button Text | White | #FFFFFF |
| Text Opacity | 100% | 1.0 |

---

## Button Specifications

### Increment Button
- **Position**: Right side of button row
- **Size**: 50% width (flex: 1)
- **Height**: 50px (paddingVertical: 15)
- **Border Radius**: 10px
- **Text**: "Increment"
- **Function**: handleIncrement()

### Decrement Button
- **Position**: Left side of button row
- **Size**: 50% width (flex: 1)
- **Height**: 50px (paddingVertical: 15)
- **Border Radius**: 10px
- **Text**: "Decrement"
- **Function**: handleDecrement()

### Reset Button
- **Position**: Below button row
- **Size**: 100% width
- **Height**: 50px (paddingVertical: 15)
- **Border Radius**: 10px
- **Text**: "Reset Counter"
- **Function**: handleReset()

### Theme Button
- **Position**: Top-right corner (absolute)
- **Coordinates**: top: 20, right: 20
- **Size**: Compact (10px vertical, 15px horizontal)
- **Border Radius**: 8px
- **Text**: "🌙 Dark" or "☀️ Light"
- **Function**: toggleTheme()

---

## Typography

### Counter Display
- **Font Size**: 72px
- **Font Weight**: Bold (700)
- **Color**: Dynamic (changes with theme)
- **Alignment**: Center

### Counter Label
- **Font Size**: 18px
- **Font Weight**: Normal (400)
- **Color**: Dynamic (changes with theme)
- **Opacity**: 0.7
- **Alignment**: Center

### Button Text
- **Font Size**: 16px
- **Font Weight**: Semi-bold (600)
- **Color**: White (#FFFFFF)
- **Alignment**: Center

### Info Text
- **Font Size**: 12px
- **Font Weight**: Normal (400)
- **Color**: Dynamic (changes with theme)
- **Opacity**: 0.5
- **Alignment**: Center

---

## Spacing & Layout

### Container
- **Padding**: 20px all sides
- **Justify Content**: Center (vertical)
- **Align Items**: Center (horizontal)
- **Flex**: 1 (full screen)

### Counter Display Section
- **Margin Bottom**: 20px
- **Alignment**: Center

### Button Row
- **Flex Direction**: Row (horizontal)
- **Justify Content**: Space-between
- **Gap**: 10px
- **Margin Bottom**: 15px
- **Width**: 100%

### Reset Button
- **Margin Bottom**: 20px
- **Width**: 100%

### Info Text
- **Position**: Absolute bottom
- **Bottom**: 20px

---

## Interaction Flow

### Counter Increment
```
User presses Increment button
    ↓
handleIncrement() called
    ↓
setCount(count + 1)
    ↓
Component re-renders
    ↓
Counter display updates
    ↓
Info text updates
```

### Counter Decrement
```
User presses Decrement button
    ↓
handleDecrement() called
    ↓
Check: if (count > 0)
    ├─ YES: setCount(count - 1)
    └─ NO: do nothing
    ↓
Component re-renders (if changed)
    ↓
Counter display updates (if changed)
    ↓
Info text updates (if changed)
```

### Counter Reset
```
User presses Reset button
    ↓
handleReset() called
    ↓
setCount(0)
    ↓
Component re-renders
    ↓
Counter display updates to 0
    ↓
Info text updates
```

### Theme Toggle
```
User presses Theme button
    ↓
toggleTheme() called
    ↓
setIsDarkMode(!isDarkMode)
    ↓
Component re-renders
    ↓
All colors update instantly
    ↓
Theme button emoji changes
    ↓
Info text updates
```

---

## Responsive Design

### Mobile Portrait
- Full width buttons
- Centered layout
- Large touch targets
- Readable text

### Mobile Landscape
- Buttons adapt to width
- Layout remains centered
- Proportional scaling

### Tablet
- Larger text
- Bigger buttons
- More padding
- Centered layout

### Desktop/Web
- Responsive width
- Centered on screen
- Keyboard support
- Mouse/touch support

---

## Accessibility Features

### Visual
- Large counter display (72px)
- High contrast colors
- Clear button labels
- Theme toggle for preferences

### Touch
- Large button targets (50px height)
- Clear visual feedback (activeOpacity)
- Responsive to touch

### Semantic
- Meaningful button labels
- Clear counter purpose
- Obvious theme toggle

---

## Animation & Feedback

### Button Press
- **Active Opacity**: 0.7 (30% dimmed)
- **Feedback**: Instant visual response
- **Duration**: Immediate

### Counter Update
- **Display Update**: Instant
- **Animation**: None (direct update)
- **Feedback**: Immediate number change

### Theme Change
- **Color Update**: Instant
- **Animation**: None (direct change)
- **Feedback**: Immediate color swap

---

## Error States

### Counter at 0
- Decrement button still visible
- Pressing does nothing
- No error message
- User-friendly behavior

### No Error States
- App is designed to prevent errors
- Validation prevents negative numbers
- No invalid states possible

---

## Success States

### Counter Incremented
- Number increases
- Info text updates
- Visual feedback on button

### Counter Decremented
- Number decreases
- Info text updates
- Visual feedback on button

### Counter Reset
- Number becomes 0
- Info text updates
- Visual feedback on button

### Theme Toggled
- All colors change
- Button emoji changes
- Info text updates
- Visual feedback on button

---

## Summary

The app provides a clean, intuitive interface with:
- ✅ Clear visual hierarchy
- ✅ Responsive layout
- ✅ Dynamic theming
- ✅ Instant feedback
- ✅ Professional appearance
- ✅ Accessible design

---

**Visual design complete and optimized!** ✨
