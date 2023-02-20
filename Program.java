//Описать базовый алгоритм волнового алгоритма (алгоритм ЛИ)
package java_home_4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Program {
    public static void main(String[] args) {
        int[][] map = generateMap();
        int startRow = 0;
        int startCol = 0;
        int endRow = 9;
        int endCol = 9;
        int[][] distance = lee(map, startRow, startCol, endRow, endCol);
        // Вывод карты расстояний на экран
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                System.out.print(distance[row][col] + " ");
            }
            System.out.println();
        }
    }
    private static final int ROW = 10; // количество строк
    private static final int COL = 10; // количество столбцов
    private static final int WALL = -1; // значение, которое обозначает стену на карте
    private static final int BLANK = 0; // значение, которое обозначает пустую клетку на карте

    // Создаем карту, заполненную случайными значениями
    public static int[][] generateMap() {
        int[][] map = new int[ROW][COL];
        Random random = new Random();
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                // Задание случайных значений
                if (random.nextDouble() < 0.2) {
                    map[row][col] = WALL;
                } else {
                    map[row][col] = BLANK;
                }
            }
        }
        return map;
    }

    // Класс для хранения координат точек на карте
    private static class Node {
        int row;
        int col;

        Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    // Основной метод, который реализует алгоритм Ли
    public static int[][] lee(int[][] map, int startRow, int startCol, int endRow, int endCol) {
        int[][] distance = new int[ROW][COL];
        Queue<Node> wavefront = new LinkedList<>();

        // Инициализация карты расстояний
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                distance[row][col] = BLANK;
            }
        }

        // Пометка начальной точки как посещенной и добавление ее в очередь волнового
        // фронта
        distance[startRow][startCol] = 1;
        wavefront.add(new Node(startRow, startCol));

        // Цикл обработки точек волнового фронта
        while (!wavefront.isEmpty()) {
            Node current = wavefront.remove();
            int currentRow = current.row;
            int currentCol = current.col;
            int currentDistance = distance[currentRow][currentCol];

            // Проверка всех соседних точек
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    // Исключение центральной точки и точек за границей карты
                    if (i == 0 && j == 0 || currentRow + i < 0 || currentRow + i >= ROW || currentCol + j < 0
                            || currentCol + j >= COL) {
                        continue;
                    }

                    // Проверка соседней точки
                    if (map[currentRow + i][currentCol + j] != WALL
                            && distance[currentRow + i][currentCol + j] == BLANK) {
                        // Пометка соседней точки значением currentDistance + 1 и добавление ее в
                        // очередь волнового фронта
                        distance[currentRow + i][currentCol + j] = currentDistance + 1;
                        wavefront.add(new Node(currentRow + i, currentCol + j));
                    }
                }
            }
        }

        // Возвращение карты расстояний
        return distance;
    }
}