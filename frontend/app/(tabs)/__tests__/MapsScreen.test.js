import React from 'react';
import { render, waitFor, fireEvent } from '@testing-library/react-native';
import { Alert } from 'react-native';
import mapsScreen from '../Maps'; // Adjust the import path according to your file structure
import * as Location from 'expo-location';
import { PermissionStatus } from 'expo-location';

// Mock `expo-location`
jest.mock('expo-location', () => ({
    requestForegroundPermissionsAsync: jest.fn(() => Promise.resolve({ status: 'granted' })),
    getCurrentPositionAsync: jest.fn(() => Promise.resolve({
        coords: {
            latitude: 1.0,
            longitude: 2.0,
        },
    })),
}));

// Mock `react-native-maps`
jest.mock('react-native-maps', () => {
    const { View } = require('react-native');
    return {
        __esModule: true,
        default: jest.fn().mockImplementation(() => <View />),
        Marker: jest.fn().mockImplementation(() => <View />),
    };
});


// Mocking Alert for testing the button press
jest.spyOn(Alert, 'alert');

describe('mapsScreen', () => {
    beforeEach(() => {
        // Reset mocks before each test
        Location.requestForegroundPermissionsAsync.mockReset();
        Location.getCurrentPositionAsync.mockReset();
        Alert.alert.mockReset();
    });

    it('renders error message if location permissions are denied', async () => {
        // Directly use the string value 'denied' without trying to access an undefined enum
        Location.requestForegroundPermissionsAsync.mockResolvedValueOnce({
            status: "denied", // Use the string value expected by your application logic
        });

        const { findByText } = render(<mapsScreen />);

        // Use findByText which returns a promise and waits for the element to appear
        const errorMessage = await findByText('Permission to access location was denied');
        expect(errorMessage).toBeTruthy();
    });

    it('fetches and displays current location if permissions are granted', async () => {
        Location.requestForegroundPermissionsAsync.mockResolvedValueOnce({ status: 'granted' });
        Location.getCurrentPositionAsync.mockResolvedValueOnce({
            coords: { latitude: 1.0, longitude: 2.0 },
        });

        const { findByTitle } = render(<mapsScreen />);
        const marker = await findByTitle('Your Location');

        expect(marker.props.coordinate).toEqual({
            latitude: 1.0,
            longitude: 2.0,
        });
    });

    it('displays the map and button correctly', () => {
        const { getByTestId, getByText } = render(<mapsScreen />);

        expect(getByTestId('MapView')).toBeTruthy();
        expect(getByText('Send hello message to GeoService')).toBeTruthy();
    });

    it('sends hello message to GeoService when button is pressed', async () => {
        const { getByText } = render(<mapsScreen />);

        // Wait for the component to be fully rendered
        await waitFor(() => {
            const button = getByText('Send hello message to GeoService');
            fireEvent.press(button);
        });

        // Assert that the alert is called
        expect(Alert.alert).toHaveBeenCalledWith('Hello GeoService!');
    });

    // Additional tests can be added here
});
