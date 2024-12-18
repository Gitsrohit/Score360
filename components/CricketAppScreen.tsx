import React, { useState } from 'react';
import { View, Text, TextInput, Image, StyleSheet, TouchableOpacity, ImageBackground, SafeAreaView } from 'react-native';

const CricketAppScreen = () => {
  const [searchText, setSearchText] = useState(''); // State for search input

  return (
    <SafeAreaView style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity style={styles.menuButton}>
          <Text style={styles.menuIcon}>☰</Text>
        </TouchableOpacity>

        {/* Search Input */}
        <TextInput
          style={styles.searchBar}
          placeholder="Search for matches..."
          placeholderTextColor="#666"
          value={searchText}
          onChangeText={(text) => setSearchText(text)}
        />

        <TouchableOpacity>
          <Text style={styles.filterIcon}>⚲</Text>
        </TouchableOpacity>
      </View>

      {/* Buttons Section */}
      <View style={styles.buttonSection}>
        <TouchableOpacity style={styles.buttonSelected}>
          <Text style={styles.buttonTextSelected}>LIVE</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>UPCOMING</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>PAST</Text>
        </TouchableOpacity>
      </View>

      {/* Background Image */}
      <ImageBackground
        source={{ uri: 'https://example.com/cricket_background.jpg' }} // Replace with an image URL
        style={styles.backgroundImage}
        resizeMode="cover"
      >
        <Image
          source={{ uri: 'https://example.com/cricket_logo.png' }} // Replace with a logo URL
          style={styles.logo}
        />
      </ImageBackground>

      {/* Footer */}
      <View style={styles.footer}>
        <TouchableOpacity style={styles.footerItem}>
          <Text style={styles.footerText}>My Matches</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.footerItem}>
          <Text style={styles.footerText}>Home</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.footerItem}>
          <Text style={styles.footerText}>Teams</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.footerItem}>
          <Text style={styles.footerText}>Settings</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

// Styles
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#003344',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 16,
    backgroundColor: '#002233',
  },
  menuButton: {
    width: 40,
    height: 40,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#004466',
    borderRadius: 20,
  },
  menuIcon: {
    fontSize: 24,
    color: '#FFFFFF',
  },
  searchBar: {
    flex: 1,
    marginHorizontal: 12,
    backgroundColor: '#FFFFFF',
    borderRadius: 20,
    paddingHorizontal: 10,
    color: '#333',
    height: 40,
  },
  filterIcon: {
    fontSize: 24,
    color: '#FFFFFF',
  },
  buttonSection: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginTop: 10,
  },
  button: {
    paddingVertical: 8,
    paddingHorizontal: 20,
    marginHorizontal: 10,
    backgroundColor: '#003344',
    borderRadius: 20,
  },
  buttonSelected: {
    paddingVertical: 8,
    paddingHorizontal: 20,
    marginHorizontal: 10,
    backgroundColor: '#004E62',
    borderRadius: 10, // Decreased border radius
    borderWidth: 2, // White stroke
    borderColor: '#FFFFFF',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
    elevation: 5,
  },
  buttonText: {
    color: '#AAA',
    fontSize: 16,
  },
  buttonTextSelected: {
    color: '#FFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
  
  backgroundImage: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  logo: {
    width: 150,
    height: 150,
    opacity: 0.8,
  },
  footer: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    paddingVertical: 10,
    backgroundColor: '#002233',
  },
  footerItem: {
    alignItems: 'center',
  },
  footerText: {
    color: '#FFFFFF',
    fontSize: 14,
  },
});

export default CricketAppScreen;
