package com.company;

import java.util.Arrays;
import java.util.Scanner;

public class BullsAndCows {
    private Scanner scanner = new Scanner(System.in);

    private int choiceOfDifficulty() {
        System.out.println("Выберите уровень сложности (3/4/5):");

        try {
            int difficulty = Integer.parseInt(scanner.nextLine());

            if (difficulty >= 3 && difficulty <= 5)
                return difficulty;
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод!");
            System.exit(0);
        }
        System.out.println("Некорректный ввод!");
        System.exit(0);
        return 0;
    }//Возвращает кол-во символов в загадке

    private int[] generateNumbers(int length){
        int min = 0;
        int max = 9;
        int[] numbers = new int[length];

        for(int i = 0; i < length; i++){
            numbers[i] = min + (int)(Math.random() * max);

            for(int j = 0; j < i; j++) {
                if (numbers[i] == numbers[j]){
                    i--;
                }
            }
        }

        return numbers;
    } //Возвращает загадку (массив случайных чисел)

    private int[] questionnaire(int length, int[] numbers){

        String userInput = "";
        int[] userIntSequence = new int[length];

        while(true){
            System.out.println("Последовательность:");
            userInput = scanner.nextLine();

            if (userInput.equals("сдаюсь")){
                System.out.println("Ответ: " + Arrays.toString(numbers));
                System.exit(0);
            }

            try{
                Integer.parseInt(userInput);

                if(userInput.length() == length){
                    break;
                }
            }
            catch(NumberFormatException e){
                System.out.println("Некорректный ввод!");
                continue;
            }

            System.out.println("Некорректное число символов!");
        }

        for (int i = 0; i < length; i++){
            userIntSequence[i] = Integer.parseInt(Character.toString(userInput.charAt(i)));
        }

        return userIntSequence;
    } //Возвращает гипотезу (строку чисел) пользователя

    private int getBulls(int[] numbers, int[] userSequence){
        int numSuccess = 0;

        for(int i = 0; i < numbers.length; i++) {
            if (numbers[i] == userSequence[i]) {
                numSuccess++;
            }
        }

        return numSuccess;
    }//Быки

    private int getCows(int[] numbers, int[] userSequence){
        int numSuccess = 0;

        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers.length; j++) {
                if (numbers[i] == userSequence[j] && i != j) {
                    numSuccess++;
                }
            }
        }

        return numSuccess;
    }//Коровы

    public void play(){
        int itemsNumber = choiceOfDifficulty();//сложность
        int[] numbers = generateNumbers(itemsNumber);//загаданные числа
        int[] userSequence;//числа пользователя
        int count = 1;

        while(true){
            userSequence = questionnaire(itemsNumber, numbers);

            int bulls = getBulls(numbers, userSequence);
            if(bulls == itemsNumber){
                System.out.println("Победа!\nКол-во попыток: " + count);
                System.out.println("Последовательность:" + Arrays.toString(numbers));
                System.exit(0);
            } else {
                System.out.println("Коровы: " + getCows(numbers, userSequence));
                System.out.println("Быки: " + getBulls(numbers, userSequence));
                count++;
            }
        }
    }
}
