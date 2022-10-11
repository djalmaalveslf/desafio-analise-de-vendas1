package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		List<Sale> sale = new ArrayList<>();
		
		System.out.print("Entre o caminho do arquivo: ");
		String strPath = sc.nextLine(); 
		try(BufferedReader br = new BufferedReader(new FileReader(strPath))){
			
			String line = br.readLine();
			
			while(line != null) {
				String[] fields = line.split(",");				
				sale.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], 
									Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			
			System.out.println("\nCinco primeiras vendas de 2016 de maior preço médio");
			sale.stream()
			.filter(s -> s.getYear() == 2016)			
			.sorted((s1, s2) -> s2.averagePrice().compareTo(s1.averagePrice()))
			.limit(5)
			.forEach(System.out::println);
			
			double sum = sale.stream()
							.filter(s -> (s.getSeller().toUpperCase().equals("LOGAN")) && (s.getMonth() == 1 || s.getMonth() == 7))
							.map(s -> s.getTotal())
							.reduce(0.0, (x, y) -> x + y);
			
			System.out.print("\nValor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", sum));
		}
		catch(IOException e) {
			System.out.println("Error " + e.getMessage());
		}
		
		sc.close();
	}
}