import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Image, Alert } from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';

const logo = require('/Users/iceberg/score/Frontend/assets/images/SCORE360.png');

const Registration = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    mobile: '',
    password: '',
    confirmPassword: '',
    otp: '',
  });
  const handleInputChange = (key, value) => {
    setFormData({ ...formData, [key]: value });
  };

  const handleSubmit = async () => {
    const { name, email, mobile, password, confirmPassword, otp } = formData;
    if (!name || !email || !mobile || !password || !confirmPassword || !otp) {
      Alert.alert('Error', 'Please fill all the fields.');
      return;
    }
    if (password !== confirmPassword) {
      Alert.alert('Error', 'Passwords do not match.');
      return;
    }
    try {
      const response = await fetch('http://127.0.0.1:8080/api/v1/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name,
          email,
          mobile,
          password,
          otp,
        }),
      });
      const data = await response.json();

      if (response.ok) {
        Alert.alert('Success', 'Registration successful!');
      } else {
        Alert.alert('Error', data.message || 'Something went wrong.');
      }
    } catch (error) {
      Alert.alert('Error', 'Failed to connect to the server.');
    }
  };

  return (
    <LinearGradient
      colors={['#000000', '#0A303B', '#36B0D5']}
      style={styles.container}
    >
      <View style={styles.logoContainer}>
        <Image source={logo} style={styles.logo} />
      </View>

      {/* Form Section */}
      <View style={styles.formContainer}>
        <Text style={styles.title}>REGISTRATION</Text>
        <TextInput
          style={styles.input}
          placeholder="Name"
          placeholderTextColor="#666"
          value={formData.name}
          onChangeText={(value) => handleInputChange('name', value)}
        />
        <TextInput
          style={styles.input}
          placeholder="Email"
          placeholderTextColor="#666"
          keyboardType="email-address"
          value={formData.email}
          onChangeText={(value) => handleInputChange('email', value)}
        />
        <TextInput
          style={styles.input}
          placeholder="Mobile No"
          placeholderTextColor="#666"
          keyboardType="phone-pad"
          value={formData.mobile}
          onChangeText={(value) => handleInputChange('mobile', value)}
        />
        <TextInput
          style={styles.input}
          placeholder="Set Password"
          placeholderTextColor="#666"
          secureTextEntry
          value={formData.password}
          onChangeText={(value) => handleInputChange('password', value)}
        />
        <TextInput
          style={styles.input}
          placeholder="Confirm Password"
          placeholderTextColor="#666"
          secureTextEntry
          value={formData.confirmPassword}
          onChangeText={(value) => handleInputChange('confirmPassword', value)}
        />
        <TextInput
          style={styles.input}
          placeholder="OTP"
          placeholderTextColor="#666"
          keyboardType="numeric"
          value={formData.otp}
          onChangeText={(value) => handleInputChange('otp', value)}
        />

        {/* Sign Up Button */}
        <TouchableOpacity style={styles.signUpButton} onPress={handleSubmit}>
          <Text style={styles.signUpButtonText}>Sign Up</Text>
        </TouchableOpacity>

        {/* Login Link */}
        <Text style={styles.loginText}>
          Already have an account?{' '}
          <Text style={styles.loginLink}>Login</Text>
        </Text>
      </View>
    </LinearGradient>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  logoContainer: {
    alignItems: 'center',
    marginTop: 50,
    marginBottom: 5,
  },
  logo: {
    width: 100,
    height: 100,
    resizeMode: 'contain',
  },
  formContainer: {
    backgroundColor: 'rgba(255, 255, 255, 0.9)',
    padding: 20,
    borderRadius: 10,
    width: '90%',
    alignSelf: 'center',
    marginTop: 20,
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 20,
    color: '#333',
    textAlign: 'center',
  },
  input: {
    width: '100%',
    backgroundColor: '#E0E0E0',
    borderRadius: 5,
    paddingHorizontal: 10,
    marginVertical: 10,
    height: 40,
    color: '#333',
  },
  signUpButton: {
    backgroundColor: '#004466',
    borderRadius: 5,
    paddingVertical: 10,
    marginTop: 20,
    width: '100%',
    alignItems: 'center',
  },
  signUpButtonText: {
    color: '#FFF',
    fontSize: 16,
    fontWeight: 'bold',
  },
  loginText: {
    marginTop: 20,
    fontSize: 14,
    color: '#555',
    textAlign: 'center',
  },
  loginLink: {
    color: '#004466',
    fontWeight: 'bold',
  },
});

export default Registration;
