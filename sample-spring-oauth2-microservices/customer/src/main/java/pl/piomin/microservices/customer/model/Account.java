package pl.piomin.microservices.customer.model;

public class Account {

	private Integer id;
	private String number;

	private Integer customerId;
	private int amount;
	public Account() {
		
	}

	public Account(Integer id, String number) {
		super();
		this.id = id;
		this.number = number;
	}

	public Account(Integer id, Integer customerId, String number, int amount) {
		this.id = id;
		this.customerId = customerId;
		this.number = number;
		this.amount = amount;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
