# Assignment 19: Digital Counter & Theme Toggle App

## Overview

A React Native mobile application that demonstrates fundamental concepts in mobile app development:
- **State Management** using React hooks (useState)
- **Counter Logic** with validation constraints
- **Dynamic Theme Toggling** (Light/Dark mode)
- **Flexbox Layout** for responsive UI design
- **Component Structure** and best practices

## Assignment Requirements

### ✅ Core Requirements Met

#### 1. **UI Layout & Component Structure**
- ✓ Parent container with centered layout using Flexbox
- ✓ Text display for counter value
- ✓ Clean button arrangement (Increment/Decrement side-by-side)
- ✓ Proper React Native style properties (flex, justifyContent, alignItems, fontSize, padding)
- ✓ Theme toggle button positioned at top-right
- ✓ Reset button spanning full width

#### 2. **Counter State & Validation Logic**
- ✓ useState hook tracks counter value (integer)
- ✓ Increment function increases count by 1
- ✓ Decrement function decreases count by 1
- ✓ **Constraint Check**: Decrement at 0 does nothing (no negative values)
- ✓ Reset function brings count back to 0
- ✓ Counter starts at 0

#### 3. **Dynamic Theme Toggling**
- ✓ useState hook tracks theme state (isDarkMode boolean)
- ✓ Light Mode (Default): White background (#FFFFFF) with dark text (#000000)
- ✓ Dark Mode: Dark gray background (#1A1A1A) with white text (#FFFFFF)
- ✓ Conditional styling using ternary operators
- ✓ Instant theme swap across entire screen
- ✓ Theme button shows current mode (☀️ Light / 🌙 Dark)

#### 4. **Code Cleanliness & Best Practices**
- ✓ Well-organized code with meaningful variable names
- ✓ Handler functions: handleIncrement, handleDecrement, handleReset, toggleTheme
- ✓ Comprehensive code comments and documentation
- ✓ No runtime crashes during interactions
- ✓ Proper component separation and structure

## Features

### Counter Operations
1. **Increment Button**: Increases counter by 1
2. **Decrement Button**: Decreases counter by 1 (minimum 0)
3. **Reset Button**: Sets counter back to 0
4. **Counter Display**: Large, centered display of current count

### Theme System
1. **Light Mode** (Default)
   - White background
   - Dark text
   - Light button colors

2. **Dark Mode**
   - Dark gray/black background
   - White text
   - Darker button colors

3. **Theme Toggle Button**: Located at top-right corner
   - Shows current mode with emoji (☀️ or 🌙)
   - Instant theme switching

### UI Elements
- **Counter Label**: "Counter Value" text above the number
- **Counter Display**: Large 72px bold number
- **Increment/Decrement Buttons**: Side-by-side layout
- **Reset Button**: Full-width button below counter buttons
- **Theme Button**: Top-right corner toggle
- **Info Text**: Bottom display showing current theme and count

## Project Structure

```
Assignment19/
├── App.js                      # Main application component
├── index.js                    # Entry point
├── package.json                # Dependencies and scripts
├── app.json                    # Expo configuration
├── babel.config.js             # Babel configuration
├── ASSIGNMENT19_README.md      # This file
├── QUICK_START.md              # Quick start guide
├── IMPLEMENTATION_DETAILS.md   # Technical details
└── FEATURES.md                 # Feature documentation
```

## Installation & Setup

### Prerequisites
- Node.js (v14+)
- npm or yarn
- Expo CLI: `npm install -g expo-cli`

### Step 1: Navigate to Project
```bash
cd Assignment19
```

### Step 2: Install Dependencies
```bash
npm install
```

### Step 3: Start Development Server
```bash
npm start
```

## Running the App

### Option 1: Web Browser (Recommended for Windows)
```bash
npm run web
```
- Opens at http://localhost:19006
- Works in any modern browser
- Best for quick testing

### Option 2: Android Device (Recommended for Android Users)
```bash
npm run android
```
**Requirements:**
- Android Studio installed
- Android Virtual Device (AVD) created and running
- OR physical Android device with USB debugging enabled

### Option 3: iOS Simulator (Mac only)
```bash
npm run ios
```
**Requirements:**
- Mac computer
- Xcode installed

### Option 4: Expo Go App (Mobile)
```bash
npm start
```
Then:
1. Download Expo Go from App Store or Google Play
2. Scan the QR code displayed in terminal
3. App opens in Expo Go

### Option 5: Physical Android Device
```bash
npm run android
```
**Setup:**
1. Connect Android device via USB
2. Enable USB debugging in developer options
3. Run the command above

## Testing Checklist

- [ ] App starts without errors
- [ ] Counter displays 0 initially
- [ ] Increment button increases counter
- [ ] Decrement button decreases counter
- [ ] Counter doesn't go below 0
- [ ] Reset button sets counter to 0
- [ ] Theme toggle changes colors
- [ ] Light mode: white background, dark text
- [ ] Dark mode: dark background, white text
- [ ] All buttons are responsive
- [ ] No console errors
- [ ] Info text updates correctly

## Code Highlights

### State Management
```javascript
const [count, setCount] = useState(0);
const [isDarkMode, setIsDarkMode] = useState(false);
```

### Counter Logic with Validation
```javascript
const handleDecrement = () => {
  if (count > 0) {
    setCount(count - 1);
  }
};
```

### Dynamic Styling
```javascript
const backgroundColor = isDarkMode ? '#1A1A1A' : '#FFFFFF';
const textColor = isDarkMode ? '#FFFFFF' : '#000000';
```

### Flexbox Layout
```javascript
container: {
  flex: 1,
  justifyContent: 'center',
  alignItems: 'center',
  padding: 20,
}
```

## Key Technologies

- **React Native**: Cross-platform mobile framework
- **Expo**: Development platform for React Native
- **React Hooks**: useState for state management
- **Flexbox**: Layout system for responsive design
- **StyleSheet**: React Native styling API

## Performance Considerations

- Minimal re-renders using React hooks
- Efficient state updates
- Optimized styling with conditional rendering
- No unnecessary component re-creation

## Accessibility

- Large, readable text (72px counter display)
- High contrast between text and background
- Clear button labels
- Responsive touch targets
- Theme toggle for user preference

## Browser Compatibility

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)
- Mobile browsers (iOS Safari, Chrome Mobile)

## Troubleshooting

### Dependencies Not Installing
```bash
npm install --legacy-peer-deps
```

### Port Already in Use
```bash
npm start -- --port 19001
```

### Clear Cache
```bash
npm start -- --clear
```

### Rebuild App
```bash
npm start -- --reset-cache
```

### Metro Bundler Issues
```bash
npm start -- --reset-cache --clear
```

## Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| App won't start | Run `npm install` again |
| Port 19006 in use | Use `npm start -- --port 19001` |
| Blank screen | Press `r` to reload |
| Theme not changing | Check isDarkMode state in console |
| Counter not updating | Verify handleIncrement/Decrement functions |

## Next Steps

1. Run the app on your device/emulator
2. Test all counter operations
3. Test theme toggle
4. Review App.js code
5. Read IMPLEMENTATION_DETAILS.md for architecture
6. Explore FEATURES.md for detailed feature documentation

## Learning Outcomes

After completing this assignment, you should understand:
- ✓ React Native component structure
- ✓ useState hook for state management
- ✓ Conditional rendering and styling
- ✓ Flexbox layout system
- ✓ Event handling in React Native
- ✓ Mobile app development best practices
- ✓ Theme/dark mode implementation
- ✓ Input validation and constraints

## Resources

- [React Native Documentation](https://reactnative.dev/)
- [Expo Documentation](https://docs.expo.dev/)
- [React Hooks Guide](https://react.dev/reference/react/hooks)
- [Flexbox Layout](https://reactnative.dev/docs/flexbox)

## Support

For issues or questions:
1. Check QUICK_START.md for setup help
2. Review IMPLEMENTATION_DETAILS.md for technical details
3. See FEATURES.md for feature documentation
4. Check React Native docs: https://reactnative.dev/

---

**Ready to build!** 🚀

Start with `npm install` then `npm start` to begin.
