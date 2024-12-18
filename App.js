import React from 'react';
import { SafeAreaView } from 'react-native';
import CricketAppScreen from './components/CricketAppScreen';

const App = () => {
  return (
    <SafeAreaView style={{ flex: 1 }}>
      <CricketAppScreen />
    </SafeAreaView>
  );
};

export default App;
