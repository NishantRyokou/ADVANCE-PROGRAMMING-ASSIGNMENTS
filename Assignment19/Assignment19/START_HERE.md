# 🚀 Assignment 19 - START HERE

## What is This?

A **React Native Digital Counter & Theme Toggle App** that demonstrates:
- State management with React hooks
- Counter logic with validation
- Dynamic theme switching (Light/Dark mode)
- Flexbox layout
- Best practices in mobile app development

---

## ⚡ Quick Start (30 seconds)

### Step 1: Install Dependencies
```bash
cd Assignment19
npm install --legacy-peer-deps
```

### Step 2: Start the App
```bash
npm start
```

### Step 3: Choose Your Platform

**Option A: Web Browser (Easiest)**
```
Press 'w' in the terminal
Opens at http://localhost:19006
```

**Option B: Android Device**
```
Press 'a' in the terminal
Requires Android Studio with AVD running
```

**Option C: iOS Simulator (Mac only)**
```
Press 'i' in the terminal
Requires Xcode
```

**Option D: Expo Go App (Mobile)**
```
Press 'w' then scan QR code
Download Expo Go from App Store or Google Play
```

---

## 📱 What Can You Do?

### Counter Operations
- **Increment Button**: Count goes up by 1
- **Decrement Button**: Count goes down by 1 (stops at 0)
- **Reset Button**: Count goes back to 0

### Theme Toggle
- **Theme Button** (top-right): Switch between Light and Dark mode
- **Light Mode**: White background, dark text
- **Dark Mode**: Dark background, white text

---

## 📁 Project Files

| File | Purpose |
|------|---------|
| `App.js` | Main application code |
| `package.json` | Dependencies and scripts |
| `app.json` | Expo configuration |
| `ASSIGNMENT19_README.md` | Complete documentation |
| `QUICK_START.md` | Quick reference guide |
| `IMPLEMENTATION_DETAILS.md` | Technical architecture |
| `FEATURES.md` | Feature documentation |
| `COMPLETION_REPORT.md` | Project completion status |

---

## ✅ Requirements Met

### Core Features
- ✅ Counter with increment/decrement/reset
- ✅ No negative numbers (validation)
- ✅ Light and dark theme modes
- ✅ Instant theme switching
- ✅ Centered Flexbox layout
- ✅ Clean, well-organized code

### Code Quality
- ✅ Meaningful variable names
- ✅ Clear function names
- ✅ Comprehensive comments
- ✅ No runtime errors
- ✅ Best practices followed

---

## 🎯 Testing Checklist

- [ ] App starts without errors
- [ ] Counter displays 0 initially
- [ ] Increment button works
- [ ] Decrement button works
- [ ] Counter doesn't go below 0
- [ ] Reset button works
- [ ] Theme toggle changes colors
- [ ] Light mode looks good
- [ ] Dark mode looks good
- [ ] All buttons are responsive

---

## 🔧 Troubleshooting

### Issue: Dependencies won't install
```bash
npm install --legacy-peer-deps
```

### Issue: Port already in use
```bash
npm start -- --port 19001
```

### Issue: Blank screen
Press `r` in the terminal to reload

### Issue: Metro Bundler error
```bash
npm start -- --reset-cache
```

---

## 📚 Documentation

### For Quick Setup
→ Read **QUICK_START.md**

### For Technical Details
→ Read **IMPLEMENTATION_DETAILS.md**

### For Feature Details
→ Read **FEATURES.md**

### For Complete Info
→ Read **ASSIGNMENT19_README.md**

---

## 🎓 What You'll Learn

- React Native fundamentals
- useState hook for state management
- Conditional rendering and styling
- Flexbox layout system
- Event handling
- Mobile app best practices
- Theme/dark mode implementation

---

## 💡 Key Code Snippets

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
```

### Dynamic Styling
```javascript
const backgroundColor = isDarkMode ? '#1A1A1A' : '#FFFFFF';
const textColor = isDarkMode ? '#FFFFFF' : '#000000';
```

---

## 🚀 Getting Started Now

1. Open terminal
2. Navigate to Assignment19 folder
3. Run: `npm install --legacy-peer-deps`
4. Run: `npm start`
5. Press `w` for web browser
6. Test the counter and theme toggle!

---

## 📞 Need Help?

1. Check **QUICK_START.md** for setup issues
2. Check **IMPLEMENTATION_DETAILS.md** for technical questions
3. Check **FEATURES.md** for feature details
4. Check **TROUBLESHOOTING.md** for common issues

---

## ✨ Features Highlight

### Counter Display
- Large, easy-to-read number (72px)
- Centered on screen
- Updates instantly

### Buttons
- Increment (right side)
- Decrement (left side)
- Reset (full width)
- Theme toggle (top-right)

### Theme System
- Light mode (default)
- Dark mode
- Instant switching
- All colors update

### UI/UX
- Responsive design
- Smooth interactions
- Clear labels
- Professional appearance

---

## 🎉 Ready to Go!

Everything is set up and ready to run. Just follow the Quick Start steps above and you'll have the app running in seconds!

**Happy coding!** 🚀

---

**Next Step**: Run `npm install --legacy-peer-deps` then `npm start`
