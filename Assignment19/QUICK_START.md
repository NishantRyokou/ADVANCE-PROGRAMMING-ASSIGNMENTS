# Quick Start - Assignment 19

## 30-Second Setup

```bash
cd Assignment19
npm install
npm start
```

Then press `w` for web or scan QR code for mobile.

## Installation

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

### Option 1: Web Browser (Easiest)
```bash
npm run web
```
- Opens at http://localhost:19006
- Works immediately in any browser

### Option 2: Android Emulator
```bash
npm run android
```
**Setup:**
1. Open Android Studio
2. Create/start an Android Virtual Device (AVD)
3. Run the command above

### Option 3: iOS Simulator (Mac only)
```bash
npm run ios
```
**Setup:**
1. Ensure Xcode is installed
2. Run the command above

### Option 4: Physical Android Device
```bash
npm run android
```
**Setup:**
1. Connect Android device via USB
2. Enable USB debugging in developer options
3. Run the command above

### Option 5: Expo Go App
```bash
npm start
```
Then:
1. Install Expo Go from App Store or Google Play
2. Scan the QR code displayed in terminal
3. App opens in Expo Go

## Features to Test

### Counter Operations
1. **Increment**: Tap "Increment" button → count increases by 1
2. **Decrement**: Tap "Decrement" button → count decreases by 1
3. **Reset**: Tap "Reset Counter" button → count returns to 0
4. **Validation**: Tap "Decrement" at 0 → nothing happens (no negative)

### Theme Toggle
1. **Toggle**: Tap theme button (☀️ Light / 🌙 Dark) in top-right
2. **Observe**:
   - Background color changes
   - Text color changes
   - Button colors change

## Project Structure

```
Assignment19/
├── App.js                      # Main app component
├── package.json                # Dependencies
├── app.json                    # Expo config
├── babel.config.js             # Babel config
└── Documentation/
    ├── ASSIGNMENT19_README.md
    ├── QUICK_START.md (this file)
    ├── IMPLEMENTATION_DETAILS.md
    └── FEATURES.md
```

## Key Files

### App.js
Main application component containing:
- State management (counter, theme)
- Handler functions (increment, decrement, reset, toggle)
- Dynamic styling
- UI components

### package.json
Project configuration with:
- Dependencies (React, React Native, Expo)
- Scripts (start, android, ios, web)

### app.json
Expo configuration with:
- App name and version
- Platform-specific settings

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

## Code Overview

### State Management
```javascript
const [count, setCount] = useState(0);
const [isDarkMode, setIsDarkMode] = useState(false);
```

### Counter Logic
```javascript
const handleIncrement = () => setCount(count + 1);
const handleDecrement = () => {
  if (count > 0) setCount(count - 1);
};
const handleReset = () => setCount(0);
```

### Theme Toggle
```javascript
const toggleTheme = () => setIsDarkMode(!isDarkMode);
```

### Dynamic Styling
```javascript
const backgroundColor = isDarkMode ? '#1A1A1A' : '#FFFFFF';
const textColor = isDarkMode ? '#FFFFFF' : '#000000';
```

## Testing Checklist

- [ ] App starts without errors
- [ ] Counter displays 0 initially
- [ ] Increment button increases counter
- [ ] Decrement button decreases counter
- [ ] Counter doesn't go below 0
- [ ] Reset button sets counter to 0
- [ ] Theme toggle changes colors
- [ ] Status bar adapts to theme
- [ ] All buttons are responsive
- [ ] No console errors

## Common Commands

```bash
# Start development server
npm start

# Run on Android
npm run android

# Run on iOS
npm run ios

# Run on web
npm run web

# Install dependencies
npm install

# Clear cache
npm start -- --reset-cache

# Stop server
Ctrl + C
```

## Tips

- Use `npm start` first, then choose platform
- Keep terminal open while developing
- Changes auto-reload on save
- Use console.log for debugging
- Check device logs for errors

## Next Steps

1. Run the app on your device/emulator
2. Test all counter operations
3. Test theme toggle
4. Review App.js code
5. Read IMPLEMENTATION_DETAILS.md for architecture
6. Explore FEATURES.md for feature details

---

**Ready to run!** 🚀

Start with `npm install` then `npm start` to begin.
