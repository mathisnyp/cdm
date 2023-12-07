import React, { useEffect, useState } from "react";
import { View, StyleSheet, TextInput, Button, Alert } from "react-native";
import { AuthControllerService, LoginDTO } from "../lib/bff-api-client";
import { useNavigation } from '@react-navigation/native';
import { useRouter } from 'expo-router';
import states from "../lib/bff-api-client/core/Global";
import alert from "../lib/bff-api-client/core/Alert";
const Login = () => {
  const navi = useNavigation()
  const router = useRouter() 
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [login, setLogin] = useState(false)
  const [loading, setLoading] = useState(false);

  const onSignInPress = async () => {
    setLoading(true);
    try {
      const requestBody: LoginDTO = {
        username: username,
        password: password,
      };
      const response = await AuthControllerService.login(requestBody);
      //Alert.alert("Success", "Login successful");
      alert("Success", "Login Successful") 
      //window.confirm("Login Successful")
      setLogin(true)
      
      //Sets username in global state
      states.user = username

      //TODO navigate to home and display username of logged in user somewhere and display some spinner while loading
      router.push("/") 
    } catch (error) {
      // Handle any errors here
      //Alert.alert("Error", "Login failed");
      alert("Error", "Failed to create user")
    } finally {
      setLoading(false);
    }
  };
  return (
    <View style={styles.container}>
      <TextInput
        autoCapitalize="none"
        placeholder="Username"
        value={username}
        onChangeText={setUsername}
        style={styles.inputField}
      />
      <TextInput
        placeholder="Password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry
        style={styles.inputField}
      />
      <Button onPress={onSignInPress} title="Login" color={"#6c47ff"} />
    </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    padding: 20,
  },
  inputField: {
    marginVertical: 4,
    height: 50,
    borderWidth: 1,
    borderColor: "#6c47ff",
    borderRadius: 4,
    padding: 10,
    backgroundColor: "#fff",
  },
  button: {
    margin: 8,
    alignItems: "center",
  },
});
export default Login;
