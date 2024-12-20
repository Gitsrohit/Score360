import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ScrollView, Image } from 'react-native';

const tournamentImage = require('/Users/iceberg/score/Frontend/assets/images/tournamentCardBg.jpg');

const Tournaments = () => {
  const [activeTab, setActiveTab] = useState('MY'); // Default active tab

  const tournaments = [
    { id: 1, title: 'TOURNAMENT A', status: 'ONGOING', date: '1 Jan, 2025 to 15 Feb, 2025', location: 'Dhanbad, Jharkhand', tab: 'MY' },
    { id: 2, title: 'TOURNAMENT B', status: 'LIVE', date: '10 Jan, 2025 to 20 Feb, 2025', location: 'Mumbai, Maharashtra', tab: 'LIVE' },
    { id: 3, title: 'TOURNAMENT C', status: 'UPCOMING', date: '15 Jan, 2025 to 25 Feb, 2025', location: 'Chennai, Tamil Nadu', tab: 'UPCOMING' },
    { id: 4, title: 'TOURNAMENT D', status: 'COMPLETED', date: '5 Dec, 2024 to 15 Dec, 2024', location: 'Delhi, India', tab: 'PAST' },
  ];

  const filteredTournaments = tournaments.filter(tournament => tournament.tab === activeTab);

  return (
    <View style={styles.container}>
      {/* Header */}
      <View style={styles.header}>
        <TouchableOpacity style={styles.filterButton}>
          <Text style={styles.filterText}>☰</Text>
        </TouchableOpacity>
        <Text style={styles.searchBar}>Search for matches...</Text>
        <TouchableOpacity style={styles.filterButton}>
          <Text style={styles.filterText}>🔍</Text>
        </TouchableOpacity>
      </View>

      {/* Toggle Buttons */}
      <View style={styles.toggleContainer}>
        {['MY', 'LIVE', 'UPCOMING', 'PAST'].map(tab => (
          <TouchableOpacity
            key={tab}
            style={[
              styles.toggleButton,
              activeTab === tab && styles.activeToggleButton,
            ]}
            onPress={() => setActiveTab(tab)}
          >
            <Text
              style={[
                styles.toggleText,
                activeTab === tab && styles.activeToggleText,
              ]}
            >
              {tab}
            </Text>
          </TouchableOpacity>
        ))}
      </View>

      {/* Tournament Cards */}
      <ScrollView contentContainerStyle={styles.cardContainer}>
        {filteredTournaments.map(tournament => (
          <View key={tournament.id} style={styles.card}>
            <Image
              source={tournamentImage}
              style={styles.cardImage}
            />
            <View style={styles.cardContent}>
              <Text style={styles.cardTitle}>{tournament.title}</Text>
              <Text style={styles.cardDetails}>{tournament.date}</Text>
              <Text style={styles.cardDetails}>📍 {tournament.location}</Text>
            </View>
            <View style={styles.statusTag}>
              <Text style={styles.statusText}>{tournament.status}</Text>
            </View>
          </View>
        ))}
      </ScrollView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#002B3D',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingVertical: 20,
    backgroundColor: '#002233',
    paddingTop:40
  },
  filterButton: {
    padding: 5,
  },
  filterText: {
    fontSize: 20,
    color: '#fff',
  },
  searchBar: {
    flex: 1,
    marginHorizontal: 10,
    backgroundColor: '#01475E',
    padding: 10,
    color: '#fff',
    borderRadius: 5,
  },
  toggleContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginTop: 10,
    paddingVertical: 6,
  },
  toggleButton: {
    paddingVertical: 10,
    paddingHorizontal: 20,
    marginHorizontal: 10,
    backgroundColor: '#003344',
    borderRadius: 10,
  },
  activeToggleButton: {
    paddingVertical: 8,
    paddingHorizontal: 20,
    marginHorizontal: 10,
    backgroundColor: '#004E62',
    borderRadius: 10,
    borderWidth: 2,
    borderColor: '#FFFFFF',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.3,
    shadowRadius: 4,
    elevation: 5,
  },
  toggleText: {
    color: '#fff',
    fontWeight: 'bold',
  },
  activeToggleText: {
     color: '#FFF', fontSize: 16, fontWeight: 'bold'
  },
  cardContainer: {
    padding: 10,
  },
  card: {
    backgroundColor: '#013A50',
    borderRadius: 10,
    overflow: 'hidden',
    marginBottom: 15,
    elevation: 5, // Adds shadow for Android
    shadowColor: '#000', // Shadow for iOS
    shadowOpacity: 0.2,
    shadowRadius: 5,
    shadowOffset: { width: 0, height: 3 },
    height: 200, // Increased card height
  },
  cardImage: {
    width: '100%',
    height: '60%', // Adjust image height to fit the new card height
    justifyContent: 'flex-end', // Align content to the bottom of the image
  },
  cardContent: {
    padding: 10,
    backgroundColor: 'rgba(0, 43, 61, 0.8)', // Slightly transparent background
  },
  cardTitle: {
    fontSize: 20, // Increased font size
    color: '#fff',
    fontWeight: 'bold', // Made text bold
  },
  cardDetails: {
    color: '#A9CCE3',
    fontSize: 14,
    marginTop: 5,
  },
  statusTag: {
    position: 'absolute',
    top: 10,
    right: 10,
    backgroundColor: '#00A86B',
    borderRadius: 5,
    padding: 5,
  },
  statusText: {
    color: '#fff',
    fontSize: 12,
    fontWeight: 'bold',
  },
});

export default Tournaments;
