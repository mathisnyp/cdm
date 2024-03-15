import React, {useMemo, useState} from "react";
import { Button, StyleSheet, TextInput, TouchableWithoutFeedback, Keyboard, ScrollView,Platform  } from "react-native";
import { useFocusEffect } from "expo-router";
import states from '../lib/bff-api-client/core/Global';
import { Formik } from "formik";
import { View, Text } from "@/components/Themed";
import { TouchableOpacity, useColorScheme } from "react-native";
import { SelectList } from 'react-native-dropdown-select-list'

interface Question {
    key: string;
    label: string;
    options?: string[];
}

interface FormValues {
    questions: Record<string, string>;
    selectedOption: string;
}

const initialFormValues: FormValues = {
    questions: {},
    selectedOption: "",
};

export const questions: Question[] = [
    { key: "question1", label: "Question 1" },
    { key: "question2", label: "Question 2" },
    { key: "multipleChoice", label: "Multiple Choice", options: ["Option 1", "Option 2", "Option 3"] },
];


export default function ReportTabScreen() {
    const [user, setUser] = useState("");
    const colorScheme = useColorScheme();

    useFocusEffect(() => {
        if (states.user !== user) {
            setUser(states.user);
        }
    });

    const countries = ["Egypt", "Canada", "Australia", "Ireland"]
    const [selected, setSelected] = React.useState("");

    return (
        <View style={styles.mainview}>
            <TouchableWithoutFeedback accessible={true}>
                <ScrollView contentContainerStyle={styles.container}>
                    <Formik
                        initialValues={initialFormValues}
                        onSubmit={(values) => {
                            console.log(values);
                        }}
                    >
                        {(props) => (
                            <View style={{flex:1}}>
                                {questions.map((question) => (
                                    <View key={question.key} style={[styles.inputContainer, { padding: 0 }]}>
                                        <Text style={styles.label}>{question.label}</Text>
                                        {question.options ? (

                                            <SelectList

                                                placeholder={'Select an item'}
                                                setSelected={(val:any) => setSelected(val)}
                                                data={question.options.map(option => ({
                                                                 label: option, // This is what the user sees
                                                                 value: option, // This is the actual value
                                                }))}
                                                
                                                inputStyles={{color: Platform.OS === 'android' ? 'white':'black'}}
                                                dropdownTextStyles={{color: Platform.OS === 'android' ? 'white':'black'}}
                                                save="value"
                                            />

                                        ) : (
                                            <TextInput
                                                style={[styles.input, { color: colorScheme === 'dark' ? 'white' : 'black' }]}
                                                placeholder={`Type your answer for ${question.label}`}
                                                onChangeText={(value) => props.setFieldValue(`questions.${question.key}`, value)}
                                                value={props.values.questions[question.key]}
                                                placeholderTextColor="#888"
                                            />
                                        )}
                                    </View>
                                ))}
                                <View style={styles.buttonRowContainer}>
                                    <TouchableOpacity
                                        style={styles.button2Container}
                                        onPress={() => {
                                            questions.forEach((question) => {
                                                const fieldName = `questions.${question.key}`;
                                                props.setFieldValue(fieldName, initialFormValues.questions[question.key]);
                                            });
                                            props.setFieldValue("selectedOption", initialFormValues.selectedOption);
                                        }}
                                    >
                                        <Text style={{ color: "white", fontSize: 19 }}>Cancel</Text>
                                    </TouchableOpacity>
                                    <TouchableOpacity style={styles.buttonContainer}>
                                        <Text style={{ color: "white", fontSize: 19 }}>Submit</Text>
                                    </TouchableOpacity>
                                </View>
                            </View>
                        )}
                    </Formik>
                </ScrollView>
            </TouchableWithoutFeedback>
        </View>
    );
}

const pickerSelectStyles = StyleSheet.create({
    inputIOS: {
        fontSize: 16,
        paddingVertical: 12,
        paddingHorizontal: 10,
        borderWidth: 1,
        borderColor: 'gray',
        borderRadius: 4,
        color: 'black',
        paddingRight: 30, // to ensure the text is never behind the icon
    },
    inputAndroid: {
        fontSize: 16,
        paddingHorizontal: 10,
        paddingVertical: 8,
        borderWidth: 0.5,
        borderColor: 'purple',
        borderRadius: 8,
        color: 'black', // Set font color for Android
        paddingRight: 30, // to ensure the text is never behind the icon
    },
});

const styles = StyleSheet.create({
    mainview:{
        flex:1,
    },
    container: {
        flexGrow:1,
        padding:16,
        paddingTop:20,
        overflow: "hidden",
    },
    inputContainer: {
        marginBottom: 15,
    },
    label: {
        fontWeight: "bold",
        fontSize: 20,
        paddingTop: 7,
        paddingBottom: 10,
    },
    input: {
        borderWidth: 1,
        borderColor: "#2e78b7",
        borderRadius: 8,
        padding: 12,
        fontSize: 16,
    },
    buttonContainer: {
        backgroundColor: "#2e78b7",
        padding: 15,
        borderRadius: 8,
        width: "45%",
        marginTop: 15,
        alignItems: "center",
    },
    button2Container: {
        backgroundColor: "red",
        color: "white",
        padding: 15,
        borderRadius: 8,
        width: "45%",
        marginTop: 15,
        alignItems: "center",
    },
    buttonRowContainer: {
        flexDirection: "row",
        justifyContent: "space-around",
        marginTop: 20,
    },
    android:{
        backgroundColor: "white",
        color: "black",
    }
});
