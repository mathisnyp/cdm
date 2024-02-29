import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react-native';
import ReportTabScreen, {questions} from '../Report';
import states from '../../lib/bff-api-client/core/Global';
import { act } from 'react-test-renderer';
// Mock external modules
jest.mock('expo-router', () => ({
    useFocusEffect: jest.fn().mockImplementation((callback) => callback()),
}));

jest.mock('../../lib/bff-api-client/core/Global', () => ({
    user: 'testUser',
}));

describe('ReportTabScreen Tests', () => {
    it('renders correctly', () => {
        const { getByText, findByPlaceholderText } = render(<ReportTabScreen />);

        questions.forEach(question => {
            expect(getByText(question.label).props.children).toBe(question.label);
            if (!question.options) {
                expect(findByPlaceholderText(`Type your answer for ${question.label}`)).toBeTruthy();
            }
        });
    });

    it('updates user state on focus', () => {
        render(<ReportTabScreen />);
        expect(states.user).toBe('testUser');
    });

    it('handles text input changes', () => {
        const { getByPlaceholderText } = render(<ReportTabScreen />);
        const input = getByPlaceholderText('Type your answer for Question 1');
        fireEvent.changeText(input, "New Answer");
        expect(input.props.value).toBe("New Answer");
    });

    it('submits form with correct values', async () => {
    //Implement this test once submission is implemented
        //     const handleSubmitSpy = jest.spyOn(ReportTabScreen.prototype, 'handleSubmit');
    //     const { getByText, getByPlaceholderText } = render(<ReportTabScreen />);
    //
    //     await act(async () => {
    //         fireEvent.changeText(getByPlaceholderText('Type your answer for Question 1'), 'Answer 1');
    //         fireEvent.changeText(getByPlaceholderText('Type your answer for Question 2'), 'Answer 2');
    //
    //         // Trigger form submission
    //         fireEvent.press(getByText('Submit'));
    //     });
    //
    //     // Expectations
    //     expect(handleSubmitSpy).toHaveBeenCalled(); // Check if the function has been called
    //     expect(handleSubmitSpy).toHaveBeenCalledWith({
    //         questions: { question1: 'Answer 1', question2: 'Answer 2', selectedOption: '' },
    //     }); // Check if the function has been called with specific arguments
    //
    //     // Clean up the spy
    //     handleSubmitSpy.mockRestore();
    });
});
