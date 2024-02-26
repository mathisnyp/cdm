import { Button, StyleSheet } from "react-native";

import EditScreenInfo from "@/components/EditScreenInfo";
import { Text, View } from "@/components/Themed";
import React, { useEffect, useState } from "react";
import { Link, useFocusEffect } from "expo-router";
import states from '../lib/bff-api-client/core/Global'
import alert from "../lib/bff-api-client/core/Alert";

export default function TabOneScreen() {
  const [user,setUser] = useState("")
  //Sets username when page is redirected to from login
  useFocusEffect(() => {
    if (states.user != user) {
      setUser(states.user)
      //alert("Success", "Login Successful")
    }
  })

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Authentication</Text>
      <View
        style={styles.separator}
        lightColor="#eee"
        darkColor="rgba(255,255,255,0.1)"
      />
      <EditScreenInfo path="app/(tabs)/index.tsx" />
      <Link href="/login" style={styles.link}>
        <Text style={styles.linkText}>Login</Text>
      </Link>
      <Link href="/register" style={styles.link}>
        <Text style={styles.linkText}>Register</Text>
      </Link>
      {user != "" &&
        <Text>Welcome, { user}</Text>
      }
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
  },
  link: {
    marginTop: 15,
    paddingVertical: 15,
  },
  linkText: {
    fontSize: 14,
    color: "#2e78b7",
  },
  separator: {
    marginVertical: 30,
    height: 1,
    width: "80%",
  },
});
