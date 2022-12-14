import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
//import java.util.Stream;
import java.lang.Integer;

class Bank {
	public String name;
	public float balance;

	public Bank(String name, float balance){
		this.name = name;
		this.balance = balance;
	}
	public float getBalance() {
		return this.balance;
	}
	@Override
	public String toString(){
		return this.name;
	}
}

class Client{
	public String name;
	public List<Bank> banks;

	public Client(String name, List<Bank> banks){
		this.name = name;
		this.banks = banks;
	}
	public List<Bank> getBanks() {
		return this.banks;
	}
	@Override
	public String toString(){
		return this.name;
	}
}

public class Test {
	public static void main(String args[]){
		LinkedList<Bank> banksA = new LinkedList<Bank>();
		LinkedList<Bank> banksB = new LinkedList<Bank>();
		LinkedList<Bank> banksC = new LinkedList<Bank>();
		LinkedList<Bank> banksD = new LinkedList<Bank>();

		banksA.add(new Bank("LCL", 1000));
		banksA.add(new Bank("SG", 300));

		banksB.add(new Bank("LCL", 400));

		banksC.add(new Bank("CA", 600));

		banksD.add(new Bank("SG", 100));

		LinkedList<Client> clients = new LinkedList<Client>();
		clients.add(new Client("A", banksA));
		clients.add(new Client("B", banksB));
		clients.add(new Client("C", banksC));
		clients.add(new Client("D", banksD));

		System.out.println(clients.stream()
			.filter(client -> 
				((Client) client).banks.stream()
					.mapToDouble(bank -> ((Bank) bank).balance)
					.sum() < 600)
			.map(client -> client.name)
			.collect(Collectors.toList())
			);
		System.out.println(clients.stream()
			.map(client -> 
				((Client) client).banks.stream()
					.mapToDouble(bank -> ((Bank) bank).balance)
					.sum())
			.reduce(0d, (total, lastSum) -> total + lastSum)
			);

		Map<String, LinkedList<Client>> groupByBank
			= new HashMap<String, LinkedList<Client>>();

		clients.stream()
				.forEach(client ->
						((Client) client).banks.stream()
						.forEach(bank -> {
							LinkedList<Client> temp
								= new LinkedList<Client>();
							temp.add(client);
							groupByBank.merge(bank.name,
								temp,
								(oldValue, newValue) -> {
									oldValue.addAll(newValue);
									return oldValue;
								});
						})
				);
		System.out.println(groupByBank);
	}
}
