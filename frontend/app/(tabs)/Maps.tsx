import React, { useEffect, useState } from "react";
import { Button, StyleSheet, View, Alert, Text,Platform } from "react-native";
import MapView, { Marker } from "react-native-maps";
import * as Location from "expo-location";
//import MapView from '@teovilla/react-native-web-maps'
// If you have a specific type for location, use that instead of any.
interface LocationState {
    coords: {
        latitude: number;
        longitude: number;
    };
}

export default function Maps() {
    const [location, setLocation] = useState<LocationState | null>(null);
    const [errorMsg, setErrorMsg] = useState<string | null>(null);

    useEffect(() => {
        (async () => {
            let { status } = await Location.requestForegroundPermissionsAsync();
            if (status !== 'granted')
            {
                setErrorMsg("Permission to access location was denied");
            }
            let location = await Location.getCurrentPositionAsync({});
            setLocation(location);
        })();
    }, []);

    return (
        <View style={styles.container}>
            {/* {errorMsg && <Text>{errorMsg}</Text>} Render the error message if it exists */}
            {/* <Text style = {{color:'white'}}>Location is : {location.coords.latitude}</Text> */}
            
            {location && (
                <Text style={{color:'white'}}>{location.coords.latitude}</Text>
            )}
            {Platform.OS !== 'web' &&
            
            
            <MapView style={styles.map} showsUserLocation={true} testID="MapView"
            initialRegion={{
                latitude: 53.350140,
                longitude: -6.266155,
                latitudeDelta:1,
                longitudeDelta:1,
              }}
            >
                {location && (
                    <>
                    {/* <Text style={{color:'white'}}>"Bruh"</Text> */}
                    <Marker
                        key={0}
                        coordinate={{
                            latitude: 53.350140,
                            longitude: -6.266155,
                        }}
                        title={"Your Location"}
                    />
                    </>
                )}
            </MapView>
            }
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
