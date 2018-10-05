package network.utils;

public interface Mutable {
	
	void mutate(float volatility);
		
	Mutable replicate();
	
}
