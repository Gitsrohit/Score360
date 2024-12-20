import React from 'react';
import { SafeAreaView } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import CricketAppScreen from './components/My matches/CricketAppScreen.tsx';
import Tournaments from './components/Tournaments/Tournaments.tsx';
import Home from './components/Home/Home';
import Teams from './components/Teams/Teams';
import Settings from './components/Settings/Settings';
import Footer from './components/Footer';

// Create a Stack Navigator instance
const Stack = createStackNavigator();

const App = () => {
  return (
    <SafeAreaView style={{ flex: 1 }}>
      <NavigationContainer>
        <Stack.Navigator screenOptions={{ headerShown: false }}>
          {/* Make sure the screen names match what you're using for navigation */}
          <Stack.Screen name="MyMatches" component={CricketAppScreen} />
          <Stack.Screen name="Tournaments" component={Tournaments} />
          <Stack.Screen name="Home" component={Home} />
          <Stack.Screen name="Teams" component={Teams} />
          <Stack.Screen name="Settings" component={Settings} />
        </Stack.Navigator>
        {/* Footer will appear outside the navigator on all screens */}
        <Footer />
      </NavigationContainer>
    </SafeAreaView>
  );
};

export default App;
