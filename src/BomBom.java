import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BomBom {
    public static void main(String[] args) {
        // Oyun haritasını okumak için kullanılacak 2D dizi
        String[][] gameMap = readMapFromFile("//Users/berfinaslan//IdeaProjects/CandyCrushGameProject//src//proje2.txt");

        // Oyun haritasını ekrana basma işlemi
        printMap(gameMap);

        // Kullanıcı girişi almak için Scanner sınıfı kullanılır
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Kullanıcıdan koordinat girişi alınır
            System.out.print("Oyun Senaryosu:    \n" +
                    "1- Lütfen koordinat giriniz: 4 1 \n" +
                    "2- Lütfen koordinat giriniz: 4 4 \n" +
                    "3- Lütfen koordinat giriniz: 0 0\n " +
                    "Lütfen koordinat giriniz : " );
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // Oyunu sonlandırmak için 0 0 koordinatları girildiğinde
            if (row == 0 && col == 0) {
                System.out.println("GÜLE GÜLE!");
                break;
            }

            // Girilen koordinatın geçerli olup olmadığını kontrol etme
            if (isValidCoordinate(row, col, gameMap)) {
                // Seçilen hücrenin sayısını al
                String targetNumber = gameMap[row][col];
                // Oyun haritasını güncelleme
                updateMap(row, col, targetNumber, gameMap);
                // Güncellenmiş oyun haritasını ekrana basma
                printMap(gameMap);
            } else {
                System.out.println("Geçersiz koordinat. Lütfen tekrar giriniz.");
            }
        }

        // Scanner nesnesini kapatma
        scanner.close();
    }

    // Dosyadan harita okuma işlemini gerçekleştiren fonksiyon
    private static String[][] readMapFromFile(String fileName) {
        String[][] map = new String[10][10];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;

            // Dosya satır satır okunur ve harita dizisine atanır
            while ((line = reader.readLine()) != null && row < 10) {
                String[] numbers = line.split(" ");

                for (int col = 0; col < Math.min(numbers.length, 10); col++) {
                    map[row][col] = numbers[col];
                }

                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    // Oyun haritasını ekrana basan fonksiyon
    private static void printMap(String[][] map) {
        System.out.println("Oyun Haritası:");
        for (String[] row : map) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    // Girilen koordinatın geçerli olup olmadığını kontrol eden fonksiyon
    private static boolean isValidCoordinate(int row, int col, String[][] map) {
        return row >= 0 && row < map.length && col >= 0 && col < map[0].length;
    }

    // Oyun haritasını güncelleyen fonksiyon (recursive)
    private static void updateMap(int row, int col, String targetNumber, String[][] map) {
        if (map[row][col].equals(targetNumber)) {
            map[row][col] = "X";  // Seçilen hücreyi 'X' karakteri ile değiştir

            // Yukarı, Sağ, Aşağı, Sol kontrolü
            int[] dRow = {-1, 0, 1, 0};
            int[] dCol = {0, 1, 0, -1};

            for (int i = 0; i < 4; i++) {
                int newRow = row + dRow[i];
                int newCol = col + dCol[i];

                if (isValidCoordinate(newRow, newCol, map) && map[newRow][newCol].equals(targetNumber)) {
                    updateMap(newRow, newCol, targetNumber, map);
                }
            }
        }
    }
}
