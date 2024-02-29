import React, { useEffect, useState } from "react";
import { Button, StyleSheet, View, Alert, Text } from "react-native";
import MapView, { Marker } from "react-native-maps";
import * as Location from "expo-location";

// If you have a specific type for location, use that instead of any.
interface LocationState {
    coords: {
        latitude: number;
        longitude: number;
    };
}

export default function Maps() {
    const [location, _] = useState<LocationState | null>(null);
    const [errorMsg, setErrorMsg] = useState<string | null>(null);

    useEffect(() => {
        (async () => {
            let { status } = await Location.requestForegroundPermissionsAsync();
            setErrorMsg("Permission to access location was denied");
        })();
    }, []);

    return (
        <View style={styles.container}>
            {errorMsg && <Text>{errorMsg}</Text>} {/* Render the error message if it exists */}
            <MapView style={styles.map} showsUserLocation={true} testID="MapView">
                {location && (
                    <Marker
                        coordinate={{
                            latitude: location.coords.latitude,
                            longitude: location.coords.longitude,
                        }}
                        title={"Your Location"}
                    />
                )}
            </MapView>
            <Button
                title={"Send hello message to GeoService"}
                onPress={() => {
                    Alert.alert("Hello GeoService!");
                }}
            />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: "center",
        justifyContent: "center",
    },
    map: {
        width: "100%",
        height: "80%",
    },
});
