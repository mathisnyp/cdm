import React, { useEffect, useState } from "react";
import {Button, StyleSheet, View, Alert, Text, Platform, TextInput} from "react-native";
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
    const [street, setStreet] = useState<string>("GPO");
    const [busMark, setBusMark] = useState<number[][]>([[0, 0], [5, 5]])
    const [incidentMark, setIncidentMark] = useState<number[][]>([[0, 0], [5, 5]])
    const [coordinates, setCoordinates] = useState<number[][]>([
        [-6.2532384, 53.3415142], [-6.252328, 53.341952]
        //[0,0], [0,5]
    ])

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
    //         await fetch("http://localhost:8090/dublin-open-data-service/bus/")
    //             .then(response => response.json())
    //             .then(json => {
    //                     setBusMark([])
    //                     console.log()
    //                     for (let i = 0;i< 100;i++) {
    //                         setBusMark(bus => [...bus,[json["entity"][i]['vehicle']['position']['latitude'],json["entity"][i]['vehicle']['position']['longitude']]])
    //                     }
    //                 }
    //
    //
    //
    //             )
    //
    //     })();
    // }, []);

    // useEffect(() => {
    //     (async () => {
    //         // let { status } = await Location.requestForegroundPermissionsAsync();
    //         // if (status !== 'granted') {
    //         //     setErrorMsg("Permission to access location was denied");
    //         // }
    //         // let location = await Location.getCurrentPositionAsync({});
    //         // setLocation(location);
    //
    //         await fetch("http://localhost:8090/geo-service/weight/updatePlots")
    //             .then(response => response.json())
    //             .then(json => {
    //                 setBusMark([]); // Clear previous bus markers
    //                 json.forEach((plot: any[]) => { // Iterate over each plot
    //                     plot.forEach(point => { // Iterate over each point in the plot
    //                         // Extract latitude and longitude values for the current point
    //                         const latitude = point.y;
    //                         const longitude = point.x;
    //                         // Add the latitude and longitude to the bus markers
    //                         setIncidentMark(bus => [...bus, [latitude, longitude]]);
    //                     });
    //                 });
    //             })
    //             .catch(error => {
    //                 console.error("Error fetching data:", error);
    //                 // Handle error
    //             });
    //         RouteControllerService.getRoute({location: startpoint, destination: street}).then(cdmPoints => {
    //             const mappedPoints = cdmPoints.map(eachPoint => {return [eachPoint.lon!!, eachPoint.lat!!]})
    //             setCoordinates(mappedPoints)
    //         })
    //     })();
    // }, []); // Empty dependency array to ensure this effect runs only once
    const handleSubmit = () => {
        // Only update the street state when the submit button is pressed
        // You can perform any validation here if needed
        // For simplicity, let's assume the input is always valid
        // Update the street state with the entered value
        setStreet(inputStreet);
    };

    useEffect(() => {
        (async () => {
            // Fetch bus data
            await fetch("http://localhost:8090/dublin-open-data-service/bus/")
                .then(response => response.json())
                .then(json => {
                    setBusMark([]);
                    for (let i = 0; i < 100; i++) {
                        setBusMark(bus => [...bus, [json["entity"][i]['vehicle']['position']['latitude'], json["entity"][i]['vehicle']['position']['longitude']]]);
                    }
                });

            // Fetch plot data
            await fetch("http://localhost:8090/geo-service/weight/updatePlots")
                .then(response => response.json())
                .then(json => {
                    setIncidentMark([]); // Clear previous incident markers
                    json.forEach((plot: any[]) => {
                        plot.forEach(point => {
                            const latitude = point.y;
                            const longitude = point.x;
                            setIncidentMark(incident => [...incident, [latitude, longitude]]);
                        });
                    });
                })
                .catch(error => {
                    console.error("Error fetching data:", error);
                    // Handle error
                });

            // Fetch route data using the updated street name
            RouteControllerService.getRoute({location: startpoint, destination: street}).then(cdmPoints => {
                const mappedPoints = cdmPoints.map(eachPoint => {
                    return [eachPoint.lon!!, eachPoint.lat!!]
                });
                setCoordinates(mappedPoints);
            });
        })();
    }, [street]); // Add street to the dependency array to run the effect when street changes


    const startpoint: PointDTO = {
        longitude: 53.341514,
        latitude: -6.253238
    }


    const geoJsonSample: FeatureCollection<Geometry, GeoJsonProperties> = {
        "type": "FeatureCollection",
        "features": [
            {
                "type": "Feature",
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
            {/* {errorMsg && <Text>{errorMsg}</Text>} Render the error message if it exists */}
            {/* <Text style = {{color:'white'}}>Location is : {location.coords.latitude}</Text> */}

            <View style={{ flexDirection: 'row', alignItems: 'center' }}>
                <TextInput
                    style={{ flex: 1, backgroundColor: 'white', padding: 10 }}
                    placeholder="Enter Street Name"
                    value={street}
                    onChangeText={text => setStreet(text)}
                />
                <Button
                    title="Submit"
                    onPress={handleSubmit}
                />
            </View>
            {location && (
                <Text style={{color: 'white'}}>{location.coords.latitude}, {location.coords.longitude}</Text>
            )}
            {Platform.OS !== 'web' &&


                <MapView style={styles.map} showsUserLocation={true} testID="MapView"
                         initialRegion={{
                             latitude: 53.350140,
                             longitude: -6.266155,
                             latitudeDelta: 1,
                             longitudeDelta: 1,
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
            {/*{ Platform.OS == 'web' &&*/}
            {/*    <Map height={500} width={960} defaultCenter={[53.350140,-6.256155]}>*/}
            {/*        /!* <Marker width={50} color={"red"} anchor={[53.350140,-6.256155]}/> *!/*/}
            {/*        <GeoJson*/}
            {/*            data={geoJsonSample}*/}
            {/*            styleCallback={(feature: { geometry: { type: string; }; }, hover: any) => {*/}
            {/*                if (feature.geometry.type === "LineString") {*/}
            {/*                    return { strokeWidth: "5", stroke: "blue"  };*/}
            {/*                }*/}
            {/*                // return {*/}
            {/*                //   fill: "#d4e6ec99",*/}
            {/*                //   strokeWidth: "1",*/}
            {/*                //   stroke: "white",*/}
            {/*                //   r: "20",*/}
            {/*                // };*/}
            {/*            }}*/}
            {/*        ></GeoJson>*/}

            {/*        {incidentMark.map(i => {*/}
            {/*            return (*/}
            {/*                <Marker*/}
            {/*                    width={20}*/}
            {/*                    anchor={[i[0],i[1]]}*/}
            {/*                />*/}
            {/*            )*/}
            {/*        })}*/}

            {/*        {busMark.map(i => {*/}
            {/*            return (*/}
            {/*                <Marker*/}
            {/*                    width={20}*/}
            {/*                    anchor={[i[0],i[1]]}*/}
            {/*                />*/}
            {/*            )*/}
            {/*        })}*/}

            {/*        /!*{ <Marker*!/*/}
            {/*        /!*    width={50}*!/*/}
            {/*        /!*    anchor={[busMark[0],busMark[1]]}*!/*/}
            {/*    </Map>*/}
            {/*}*/}
            {Platform.OS == 'web' && (
                <Map height={500} width={960} defaultCenter={[53.350140,-6.256155]}>
                    <GeoJson
                        data={geoJsonSample}
                        styleCallback={(feature: { geometry: { type: string; }; }, hover: any) => {
                            if (feature.geometry.type === "LineString") {
                                return { strokeWidth: "5", stroke: "blue" };
                            }
                        }}
                    />
                    {/* Render incident markers */}
                    {incidentMark.map((i, index) => (
                        <Marker
                            key={`incident-${index}`}
                            width={20}
                            color="red" // Set color to red for incident markers
                            anchor={[i[0], i[1]]}
                        />
                    ))}
                    {/* Render bus markers */}
                    {busMark.map((i, index) => (
                        <Marker
                            key={`bus-${index}`}
                            width={20}
                            color="blue" // Set color to blue for bus markers
                            anchor={[i[0], i[1]]}
                        />
                    ))}
                </Map>
            )}
            <Button
                title={"Send hello message to GeoService"}
                onPress={() => {
                    Alert.alert("Hello GeoService!");
                    setStreet(street)
                }}
            />
        </View>
    );
}
//     return (
//         <View style={styles.container}>
//             {/* Add your error message rendering here if needed */}
//             <Text style={{color:'white'}}>Location is : {location ? `${location.coords.latitude}, ${location.coords.longitude}` : ''}</Text>
//             {/* Form for submitting street name */}
//             <View style={{ flexDirection: 'row', alignItems: 'center' }}>
//                 <TextInput
//                     style={{ flex: 1, backgroundColor: 'white', padding: 10 }}
//                     placeholder="Enter Street Name"
//                     value={street}
//                     onChangeText={text => setStreet(text)}
//                 />
//                 <Button
//                     title="Submit"
//                     onPress={handleSubmit}
//                 />
//             </View>
//
//             {/* Render Map based on platform */}
//             {Platform.OS !== 'web' && (
//                 <MapView
//                     style={styles.map}
//                     showsUserLocation={true}
//                     initialRegion={{
//                         latitude: 53.350140,
//                         longitude: -6.266155,
//                         latitudeDelta: 1,
//                         longitudeDelta: 1,
//                     }}
//                 >
//                     {/* Add your GeoJson and Marker components here */}
//                 </MapView>
//             )}
//
//             {Platform.OS === 'web' && (
//                 <Map
//                     height={500}
//                     width={960}
//                     defaultCenter={[53.350140,-6.256155]}
//                 >
//                     {/* Add your GeoJson and Marker components here */}
//                 </Map>
//             )}
//
//             {/* Button for sending hello message */}
//             <Button
//                 title="Send hello message to GeoService"
//                 onPress={() => {
//                     Alert.alert("Hello GeoService!");
//                     setStreet("GPO"); // Update the street name here if needed
//                 }}
//             />
//         </View>
//     );
// }

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
