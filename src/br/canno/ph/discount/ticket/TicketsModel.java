package br.canno.ph.discount.ticket;

import br.canno.ph.car.type.Vehicle;
import br.canno.ph.discount.DiscountModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicketsModel {

    public DiscountModel discountModel = new DiscountModel();

    public TicketsModel() {
    }

    public void initialProceed() throws IOException, InterruptedException {
        final String directory = (LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")).replaceAll("-", ""));
        File file = new File(directory);
        if (!file.exists()) {
            Files.createDirectory(Paths.get("", directory));
        }

        Thread.sleep(500L);
    }

    public void prize(Vehicle vehicle) {
        switch (vehicle.getVehicleType()) {
            case MOTO:
                prizeTickets(vehicle, 10, TicketsType.GOLD);
                prizeTickets(vehicle, 25, TicketsType.SILVER);
                prizeTickets(vehicle, 65, TicketsType.STANDARD);
                break;
            case VOITURE:
                prizeTickets(vehicle, 15, TicketsType.GOLD);
                prizeTickets(vehicle, 65, TicketsType.SILVER);
                prizeTickets(vehicle, 20, TicketsType.STANDARD);
                break;
            case CAMIONETTE:
                prizeTickets(vehicle, 50, TicketsType.GOLD);
                prizeTickets(vehicle, 35, TicketsType.SILVER);
                prizeTickets(vehicle, 15, TicketsType.STANDARD);
                break;
            default:
                break;
        }
    }

    public void prizeTickets(Vehicle vehicle, int chance, TicketsType ticketsType) {
        Random random = new Random();

        int faith = random.nextInt(100);

        if (!vehicle.isReceived()) {
            for (int i = 1; i < chance; i++) {
                if (random.nextInt(100) == faith || (ticketsType.equals(TicketsType.STANDARD) && i == (chance - 1))) {
                    try {
                        String newDirectory = (LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy")).replaceAll("-", ""));

                        File file = new File("" + newDirectory + "/" + vehicle.getLicensePlate() + "_" + ticketsType.getPreview() + ".txt");
                        file.createNewFile();

                        try (FileWriter fileWriter = new FileWriter("" + newDirectory + "/" + vehicle.getLicensePlate() + "_" + ticketsType.getPreview() + ".txt")) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("--------------------" + "\r\n");
                            stringBuilder.append("Date: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy"))).append("\r\n");
                            stringBuilder.append("Seat occupied: ").append(vehicle.getId()).append("\r\n");
                            stringBuilder.append("Vehicle type: ").append(vehicle.getVehicleType().name()).append("\r\n");
                            stringBuilder.append("Base price: ").append(vehicle.getVehicleType().getPrice()).append(" euros\r\n");
                            
                            if (discountModel.checkDiscount(vehicle).isDay()) {
                                stringBuilder.append("Total to pay: ").append(discountModel.discountByDay(vehicle)).append("0 euros\r\n");
                                stringBuilder.append("Reduction: ").append(discountModel.checkDiscount(vehicle).name()).append(" - ").append(discountModel.checkDiscount(vehicle).getDescription()).append("\r\n");
                            }
                            
                            stringBuilder.append("Cinema Promo Code: ").append(generateCinemaCode()).append("\r\n");
                            
                            stringBuilder.append("\r\n");
                            
                            vehicle.setReceived(true);
                            
                            switch (ticketsType) {
                                case GOLD:
                                    boolean winner = false;
                                    char[][] gold = prizeGold();
                                    for (int x = 0; x <= 2; x++) {
                                        for (int y = 0; y <= 2; y++) {
                                            if (gold[x][y] == gold[y][x]) {
                                                if (gold[y][1] == gold[x][y]) {
                                                    winner = true;
                                                } else if (gold[y][2] == gold[x][y]) {
                                                    winner = true;
                                                }
                                                break;
                                            }
                                        }
                                    }   
                                    stringBuilder.append("Gold Ticket: Value: ").append(winner ? "40%" : "20%").append("\r\n");
                                    stringBuilder.append("Game: \r\n");
                                    stringBuilder.append("").append(gold[0][0]).append(gold[0][1]).append(gold[0][2]).append("\r\n");
                                    stringBuilder.append("").append(gold[1][0]).append(gold[1][1]).append(gold[1][2]).append("\r\n");
                                    stringBuilder.append("").append(gold[2][0]).append(gold[2][1]).append(gold[2][2]).append("\r\n");
                                    break;
                                case STANDARD:
                                    stringBuilder.append("Standard Ticket: Value: 5%\r\n");
                                    break;
                                case SILVER:
                                    boolean doubleSilverX = false;
                                    int counter = 0;
                                    char[] inp = prizeSilver().toCharArray();
                                    for (int n = 0; n < prizeSilver().length(); n++) {
                                        for (int j = n + 1; j < prizeSilver().length(); j++) {
                                            if (inp[n] == inp[j]) {
                                                if (inp[j] == 'X') {
                                                    doubleSilverX = true;
                                                }
                                                
                                                counter++;
                                            }
                                        }
                                    }   
                                    stringBuilder.append("Silver Ticket: Value: ").append(doubleSilverX ? "30%" : (counter >= 1 ? "15%" : "10%")).append("\r\n");
                                    stringBuilder.append("").append(prizeSilver().toString()).append("\r\n");
                                    break;
                                default:
                                    break;
                            }
                            
                            stringBuilder.append("--------------------");
                            
                            fileWriter.write(stringBuilder.toString());
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                    }
                    break;
                }
            }
        }
    }

    public char[][] prizeGold() {
        Random random = new Random();
        List<String> magicWord = new ArrayList<>();

        magicWord.add("P");
        magicWord.add("A");
        magicWord.add("R");
        magicWord.add("K");
        magicWord.add("H");
        magicWord.add("E");
        magicWord.add("L");
        magicWord.add("B");

        char mix[][] = {
            {magicWord.get(random.nextInt(7)).charAt(0), magicWord.get(random.nextInt(7)).charAt(0), magicWord.get(random.nextInt(7)).charAt(0)},
            {magicWord.get(random.nextInt(7)).charAt(0), magicWord.get(random.nextInt(7)).charAt(0), magicWord.get(random.nextInt(7)).charAt(0)},
            {magicWord.get(random.nextInt(7)).charAt(0), magicWord.get(random.nextInt(7)).charAt(0), magicWord.get(random.nextInt(7)).charAt(0)}
        };
        return mix;
    }

    public String prizeSilver() {
        Random random = new Random();
        List<String> silverTickets = new ArrayList<>();
        silverTickets.add("O");
        silverTickets.add("X");
        silverTickets.add("P");

        String silverTicket = silverTickets.get(random.nextInt(3)) + silverTickets.get(random.nextInt(3)) + silverTickets.get(random.nextInt(3));

        return silverTicket;
    }

    public String generateCinemaCode() {
        Random random = new Random();
        return "CIN" + random.nextInt(1000) + "FRA";
    }

    public static enum TicketsType {

        STANDARD("std"), SILVER("sil"), GOLD("gol");

        private final String preview;

        private TicketsType(String preview) {
            this.preview = preview;
        }

        public String getPreview() {
            return preview;
        }

    }

}
