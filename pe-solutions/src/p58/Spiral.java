/**
 * 
 */
package p58;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Saimir Bala
 *
 */
public class Spiral {
	private ArrayList<Number> spiral;
	private Collection<Number> principalDiagonal; //from top left to bottom right
	private Collection<Number> secondaryDigonal;
	
	public Spiral() {
		spiral = new ArrayList<Number>();
		spiral.add(1);
		principalDiagonal = new HashSet<Number>();
		secondaryDigonal = new HashSet<Number>();
	}

	private void wrapHalfLayer(){
		int length = spiral.size();
		int newLength = (int) Math.pow(Math.sqrt(length)+1, 2);
		for(int i = length; i<newLength; i++)
			spiral.add(i+1);
	}
	
	public void wrapOneLayer() {
		wrapHalfLayer();
		wrapHalfLayer();
	}
	
	public void recomputeDiagonals(){
		int length = this.getSideLength();
		int delta = 0;
		
		if(length==1){
			principalDiagonal = secondaryDigonal = spiral;
			return;
		}
		
		if(length % 2 == 0){
			delta = length;
			for(int i = 0; i<length; i+=delta){
				principalDiagonal.add(spiral.get(i));
				secondaryDigonal.add(spiral.get(i+1));
			}	
		}	
		else{//this is the case for One Layer Wraps
			principalDiagonal.add(spiral.get(0));
			secondaryDigonal.add(spiral.get(0));
			delta = length-1; //start with this len = 2
			
//			for(int i = 0; i<spiral.size();){
			for(int i = spiral.size()-1; i>0;){
				for(int angles = 4; angles > 0 && i>0; angles--){
					if(angles%2==0){
						if(!principalDiagonal.add(spiral.get(i)))
							return; //it means that I am inserting an alraedy existing number. no need to continue
					}
					else{
						if(!secondaryDigonal.add(spiral.get(i)))
							return;
					}
					i-=delta;
				}
				delta-=2;
			}	
		}
	}
	
	/**
	 * @return the principalDiagonal
	 */
	public Collection<Number> getPrincipalDiagonal() {
		return principalDiagonal;
	}

	/**
	 * @return the secondaryDigonal
	 */
	public Collection<Number> getSecondaryDigonal() {
		return secondaryDigonal;
	}

	/**
	 * @return the spiral
	 */
	public ArrayList<Number> getSpiral() {
		return spiral;
	}	

	/**
	 * @return the length
	 */
	public int getSideLength() {
		return (int) Math.sqrt(spiral.size());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Spiral [spiral=" + spiral + "]";
	}	
}
