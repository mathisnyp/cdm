import React, { useState } from "react";
import { View, TextInput, Button, StyleSheet, Alert } from "react-native";
import { AuthControllerService, CreateUserDTO } from "../lib/bff-api-client";
import { useRouter } from "expo-router";
import alert from "../lib/bff-api-client/core/Alert";
import states from "../lib/bff-api-client/core/Global";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const router = useRouter()
  const onSignUpPress = async () => {
    setLoading(true);
    try {
      const requestBody: CreateUserDTO = {
        username: username,
        password: password,
      };
      const response = await AuthControllerService.createUser(requestBody);
      // TODO navigate to login screen and display some spinner while loading
      //Alert.alert("Success", "User created successfully");
      states.user = username
      alert("Success", "User Registered Successfully")
      router.push('/')

    } catch (error) {
      // Handle any errors here, such as displaying an alert
      //Alert.alert("Error", "Failed to create user");
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
      <Button onPress={onSignUpPress} title="Sign up" color={"#6c47ff"} />
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
});

export default Register;
