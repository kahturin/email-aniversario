package br.com.enviodeemails;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private static final String ARQUIVO_EXCEL = "/massa/Bradesco aniversariantes v16_01_25.xlsx";
    private static final String ARQUIVO_CONFIG = "/massa/config.xlsx";

    public static List<Pessoa> getAniversariantesHoje() {
        List<Pessoa> aniversariantes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
        LocalDate hoje = LocalDate.now();

        try (InputStream file = ExcelReader.class.getResourceAsStream(ARQUIVO_EXCEL);
             Workbook workbook = new XSSFWorkbook(file)) {

            if (file == null) {
                System.out.println("Arquivo não encontrado no caminho: " + ARQUIVO_EXCEL);
                return aniversariantes;
            }

            Sheet sheet = workbook.getSheetAt(0);
            int ultimaLinha = sheet.getLastRowNum();
            for (int i = 1; i <= ultimaLinha; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Cell nomeCell = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                Cell emailCell = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                Cell dataCell = row.getCell(10, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

                if (nomeCell == null && emailCell == null && dataCell == null) continue;

                if (nomeCell == null || emailCell == null) {
                    System.out.println("Erro: nome ou e-mail ausente na linha " + (i + 1));
                    continue;
                }

                String nome = nomeCell.getStringCellValue().trim();
                String email = emailCell.getStringCellValue().trim();

                // data nasc == dia/mês de hoje
                if (dataCell == null || dataCell.getCellType() == CellType.BLANK) {
                    System.out.println("Aviso: Data de nascimento vazia na linha " + (i + 1));
                    continue;
                }
                LocalDate dataNascimento = null;

                try {
                    if (dataCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dataCell)) {
                        dataNascimento = dataCell.getLocalDateTimeCellValue().toLocalDate();
                    } else if (dataCell.getCellType() == CellType.STRING) {
                        String dataTexto = dataCell.getStringCellValue().trim();
                        if (!dataTexto.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            throw new IllegalArgumentException("Formato de data inválido.");
                        }
                        dataNascimento = LocalDate.parse(dataTexto, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    } else {
                        throw new IllegalArgumentException("Formato de data inválido!!");
                    }
                } catch (Exception e) {
                    System.out.println("Erro: Formato de data inválida na linha " + (i + 1));
                    continue;
                }

                if (dataNascimento.format(formatter).equals(hoje.format(formatter))) {
                    aniversariantes.add(new Pessoa(nome, email));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Total de aniversariantes encontrados: " + aniversariantes.size());
        return aniversariantes;
    }

    public static String getEmailOutlook() {
        try (InputStream file = ExcelReader.class.getResourceAsStream(ARQUIVO_CONFIG);
             Workbook workbook = new XSSFWorkbook(file)) {

            if (file == null) {
                System.out.println("Arquivo de configuração não encontrado");
                return null;
            }

            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            if (row != null) {
                Cell cell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                if (cell != null) {
                    return cell.getStringCellValue().trim();
                }
            }
            System.out.println("Nenhum e-mail encontrado no arquivo de configuração.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}