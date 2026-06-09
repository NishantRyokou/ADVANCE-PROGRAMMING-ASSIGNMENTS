import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

/**
 * Assignment 19: Digital Counter & Theme Toggle App
 * 
 * A React Native application that demonstrates:
 * - State management using useState hook
 * - Counter logic with validation (no negative numbers)
 * - Dynamic theme toggling (Light/Dark mode)
 * - Flexbox layout for responsive UI
 * - Clean component structure and best practices
 */
export default function App() {
  // State Management
  const [count, setCount] = useState(0);
  const [isDarkMode, setIsDarkMode] = useState(false);

  // Counter Logic - Handler Functions
  /**
   * Increment counter by 1
   */
  const handleIncrement = () => {
    setCount(count + 1);
  };

  /**
   * Decrement counter by 1
   * Constraint: Never allow counter to go below 0
   */
  const handleDecrement = () => {
    if (count > 0) {
      setCount(count - 1);
    }
  };

  /**
   * Reset counter to 0
   */
  const handleReset = () => {
    setCount(0);
  };

  /**
   * Toggle between Light Mode and Dark Mode
   */
  const toggleTheme = () => {
    setIsDarkMode(!isDarkMode);
  };

  // Dynamic Styling based on Theme State
  const backgroundColor = isDarkMode ? '#1A1A1A' : '#FFFFFF';
  const textColor = isDarkMode ? '#FFFFFF' : '#000000';
  const buttonColor = isDarkMode ? '#0A84FF' : '#007AFF';
  const buttonTextColor = '#FFFFFF';
  const secondaryButtonBg = isDarkMode ? '#333333' : '#E8E8E8';
  const secondaryButtonText = isDarkMode ? '#FFFFFF' : '#000000';

  // StyleSheet with Flexbox Layout
  const styles = StyleSheet.create({
    // Main container - centered using Flexbox
    container: {
      flex: 1,
      backgroundColor: backgroundColor,
      justifyContent: 'center',
      alignItems: 'center',
      padding: 20,
    },

    // Counter display section
    counterDisplay: {
      fontSize: 72,
      fontWeight: 'bold',
      color: textColor,
      marginBottom: 40,
    },

    counterLabel: {
      fontSize: 18,
      color: textColor,
      marginBottom: 10,
      opacity: 0.7,
    },

    // Button row - side-by-side layout using Flexbox
    buttonRow: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      width: '100%',
      marginBottom: 15,
      gap: 10,
    },

    // Primary buttons (Increment/Decrement)
    button: {
      flex: 1,
      paddingVertical: 15,
      paddingHorizontal: 20,
      borderRadius: 10,
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: buttonColor,
    },

    buttonText: {
      fontSize: 16,
      fontWeight: '600',
      color: buttonTextColor,
    },

    // Reset button styling
    resetButton: {
      width: '100%',
      paddingVertical: 15,
      paddingHorizontal: 20,
      borderRadius: 10,
      justifyContent: 'center',
      alignItems: 'center',
      backgroundColor: secondaryButtonBg,
      marginBottom: 20,
    },

    resetButtonText: {
      fontSize: 16,
      fontWeight: '600',
      color: secondaryButtonText,
    },

    // Theme toggle button - positioned at top-right
    themeButton: {
      position: 'absolute',
      top: 20,
      right: 20,
      paddingVertical: 10,
      paddingHorizontal: 15,
      borderRadius: 8,
      backgroundColor: secondaryButtonBg,
    },

    themeButtonText: {
      fontSize: 14,
      fontWeight: '600',
      color: secondaryButtonText,
    },

    // Info text at bottom
    infoText: {
      position: 'absolute',
      bottom: 20,
      fontSize: 12,
      color: textColor,
      opacity: 0.5,
    },
  });

  return (
    <View style={styles.container}>
      {/* Theme Toggle Button - Top Right */}
      <TouchableOpacity
        style={styles.themeButton}
        onPress={toggleTheme}
        activeOpacity={0.7}
      >
        <Text style={styles.themeButtonText}>
          {isDarkMode ? '☀️ Light' : '🌙 Dark'}
        </Text>
      </TouchableOpacity>

      {/* Counter Display Section - Centered */}
      <View style={{ alignItems: 'center', marginBottom: 20 }}>
        <Text style={styles.counterLabel}>Counter Value</Text>
        <Text style={styles.counterDisplay}>{count}</Text>
      </View>

      {/* Button Row - Increment and Decrement Side-by-Side */}
      <View style={styles.buttonRow}>
        <TouchableOpacity
          style={styles.button}
          onPress={handleDecrement}
          activeOpacity={0.7}
        >
          <Text style={styles.buttonText}>Decrement</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.button}
          onPress={handleIncrement}
          activeOpacity={0.7}
        >
          <Text style={styles.buttonText}>Increment</Text>
        </TouchableOpacity>
      </View>

      {/* Reset Button - Full Width */}
      <TouchableOpacity
        style={styles.resetButton}
        onPress={handleReset}
        activeOpacity={0.7}
      >
        <Text style={styles.resetButtonText}>Reset Counter</Text>
      </TouchableOpacity>

      {/* Info Text - Bottom Display */}
      <Text style={styles.infoText}>
        Theme: {isDarkMode ? 'Dark' : 'Light'} | Count: {count}
      </Text>
    </View>
  );
}
