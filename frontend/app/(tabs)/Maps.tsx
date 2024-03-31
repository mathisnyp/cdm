import React, { useEffect, useState } from "react";
import { Button, StyleSheet, View, Alert, Text,Platform } from "react-native";
import MapView, {Geojson} from "react-native-maps";
import {Map, Marker, GeoJson} from 'pigeon-maps'
import * as Location from "expo-location";
import { CdmPoint, PointDTO, RouteControllerService } from "../lib/geoservice";
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
    const [coordinates, setCoordinates] = useState<number[][]>([
        [-6.2532384, 53.3415142],[-6.252328,53.341952]
    ]) 
    useEffect(() => {
        (async () => {
            let { status } = await Location.requestForegroundPermissionsAsync();
            if (status !== 'granted')
            {
                setErrorMsg("Permission to access location was denied");
            }
            let location = await Location.getCurrentPositionAsync({});
            setLocation(location);
            const k = await fetch("http://localhost:8090/geo/api/route",{
                method: "GET", // *GET, POST, PUT, DELETE, etc.
                mode: "cors", // no-cors, *cors, same-origin
                // headers: {
                //     "Content-Type": "application/json",
                //     // 'Content-Type': 'application/x-www-form-urlencoded',
                // },
                // body: JSON.stringify({location: startpoint, destination: "GPO"})
            })

            const c = k.text()
            console.log(c)

            RouteControllerService.getRoute({location: startpoint, destination: "GPO"}).then(cdmPoints => {
                const mappedPoints = cdmPoints.map(eachPoint => {return [eachPoint.lon!!, eachPoint.lat!!]})
                console.log(mappedPoints)
                setCoordinates(mappedPoints)
            })
        })();
    }, []);

    const startpoint:PointDTO = {
        longitude : 53.3415142,
        latitude : -6.2532384
    }
    
    

    

    const geoJsonSample = { 
        "type": "FeatureCollection",
        "features": [
        { "type": "Feature",
            "geometry": {
            "type": "LineString",
            "coordinates": [
                coordinates[0],coordinates[1]
            ]
            },
            },
        ]
     }
    
    

    return (
        <View style={styles.container}>
            {/* {errorMsg && <Text>{errorMsg}</Text>} Render the error message if it exists */}
            {/* <Text style = {{color:'white'}}>Location is : {location.coords.latitude}</Text> */}
            
            {location && (
                <Text style={{color:'white'}}>{location.coords.latitude}, {location.coords.longitude}</Text>
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
                <Geojson
                    geojson={geoJsonSample}
                    strokeColor="red"
                    fillColor="green"
                    strokeWidth={5}
                    />
                {location && (
                    <>
                    {/* <Marker
                        key={0}
                        Coordinate={{
                            latitude: 53.350140,
                            longitude: -6.266155,
                        }}
                        title={"Your Location"}
                    /> */}
                    </>
                )}
            </MapView>
            // <Map height={300} defaultCenter={[53.350140,-6.256155]}>
            //     <Marker width={50} color={"red"} anchor={[53.350140,-6.256155]}/>
            // </Map>
            }
            { Platform.OS == 'web' && 
                <Map height={500} width={960} defaultCenter={[53.350140,-6.256155]}>
                    {/* <Marker width={50} color={"red"} anchor={[53.350140,-6.256155]}/> */}
                    <GeoJson
                        data={geoJsonSample}
                        styleCallback={(feature, hover) => {
                            if (feature.geometry.type === "LineString") {
                              return { strokeWidth: "5", stroke: "blue"  };
                            }
                            // return {
                            //   fill: "#d4e6ec99",
                            //   strokeWidth: "1",
                            //   stroke: "white",
                            //   r: "20",
                            // };
                        }}
                    ></GeoJson>
                    {location !== null && (
                    <>
                    <Marker
                        width={50}
                        anchor={[location.coords.latitude,location.coords.longitude]}
                    />
                    </>
                )}
                </Map>
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
