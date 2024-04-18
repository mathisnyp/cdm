import React, { useEffect, useState } from "react";
import { Button, StyleSheet, View, Alert, Text,Platform } from "react-native";
import MapView, {Geojson} from "react-native-maps";
import {Map, Marker, GeoJson} from 'pigeon-maps'
import * as Location from "expo-location";
import { PointDTO, RouteControllerService } from "../lib/geoservice";
import {FeatureCollection, GeoJsonProperties, Geometry} from "geojson";
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
    const [busMark, setBusMark] = useState<number[][]>([[0,0],[5,5]])
    const [incidentMark, setIncidentMark] = useState<number[][]>([[0,0],[5,5]])

    // useEffect(() => {
    //     (async () => {
    //         // let { status } = await Location.requestForegroundPermissionsAsync();
    //         // if (status !== 'granted')
    //         // {
    //         //     setErrorMsg("Permission to access location was denied");
    //         // }
    //         // let location = await Location.getCurrentPositionAsync({});
    //         // setLocation(location);
    //
    //         await fetch("http://localhost:8090/geo-service/weight/updatePlots")
    //             .then(response => response.json())
    //             .then(json => {
    //                     setBusMark([])
    //                     console.log()
    //                     for (let i = 0;i< 100;i++) {
    //
    //                         // console.pri
    //                         // setBusMark(bus => [...bus,[json["entity"][i]['vehicle']['position']['latitude'],json["entity"][i]['vehicle']['position']['longitude']]])
    //                     }
    //                 }
    //
    //
    //
    //             )
    //
    //     })();
    // }, []);

    useEffect(() => {
        (async () => {
            // let { status } = await Location.requestForegroundPermissionsAsync();
            // if (status !== 'granted') {
            //     setErrorMsg("Permission to access location was denied");
            // }
            // let location = await Location.getCurrentPositionAsync({});
            // setLocation(location);

            await fetch("http://localhost:8090/geo-service/weight/updatePlots")
                .then(response => response.json())
                .then(json => {
                    setBusMark([]); // Clear previous bus markers
                    json.forEach((plot: any[]) => { // Iterate over each plot
                        plot.forEach(point => { // Iterate over each point in the plot
                            // Extract latitude and longitude values for the current point
                            const latitude = point.y;
                            const longitude = point.x;
                            // Add the latitude and longitude to the bus markers
                            setIncidentMark(bus => [...bus, [latitude, longitude]]);
                        });
                    });
                })
                .catch(error => {
                    console.error("Error fetching data:", error);
                    // Handle error
                });
        })();
    }, []); // Empty dependency array to ensure this effect runs only once

    const startpoint:PointDTO = {
        longitude : 53.341514,
        latitude : -6.253238
    }
    
    

    

    const geoJsonSample: FeatureCollection<Geometry, GeoJsonProperties> = {
        "type": "FeatureCollection",
        "features": [
        { "type": "Feature",
            "geometry": {
            "type": "LineString",
            "coordinates": []
            },
            properties: {}
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
                        styleCallback={(feature: { geometry: { type: string; }; }, hover: any) => {
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

                    {incidentMark.map(i => {
                        return (
                            <Marker
                                width={20}
                                anchor={[i[0],i[1]]}
                            />
                        )
                    })}

                    {/* <Marker
                        width={50}
                        anchor={[busMark[0],busMark[1]]}
                    /> */}
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
