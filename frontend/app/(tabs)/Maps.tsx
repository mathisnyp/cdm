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
    const [location, setLocation] = useState<LocationState>({'coords':
        {latitude : 0,longitude : 0}
});
    const [errorMsg, setErrorMsg] = useState<string | null>(null);
    const [coordinates, setCoordinates] = useState<number[][]>([
        [-6.2532384, 53.3415142],[-6.252328,53.341952]
        //[0,0], [0,5]
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

            
            RouteControllerService.getRoute({location: startpoint, destination: "GPO"}).then(cdmPoints => {
                const mappedPoints = cdmPoints.map(eachPoint => {return [eachPoint.lon!!, eachPoint.lat!!]})
                setCoordinates(mappedPoints)
            })
        })();
    }, []);

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
            "coordinates": coordinates
            },
            properties: {}
            },
        ]
     }
    
    

    return (
        <View style={styles.container}>
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
            </MapView>
            }
            { Platform.OS == 'web' && 
                <Map height={500} width={960} defaultCenter={[53.350140,-6.256155]}>
                    <GeoJson
                        data={geoJsonSample}
                        styleCallback={(feature: { geometry: { type: string; }; }, hover: any) => {
                            if (feature.geometry.type === "LineString") {
                              return { strokeWidth: "5", stroke: "blue"  };
                            }
                        }}
                    ></GeoJson>
                    <Marker
                        width={50}
                        anchor={[location.coords.latitude, location.coords.longitude]}
                    />
                    
                    {(
                        <>

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
