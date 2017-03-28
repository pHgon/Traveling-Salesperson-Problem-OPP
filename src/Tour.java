 public class Tour {
	private Node first;

	public Tour() {
		 this.first=null;    // Referencia o primeiro nodo
	}
	
	// Lista Circular para Debug
	public Tour(Point a ,Point b ,Point c, Point d){
		this.first = new Node(); 
		this.first.p = a;

		this.first.next = new Node();
		this.first.next.p = b;

		this.first.next.next = new Node();
		this.first.next.next.p = c;

		this.first.next.next.next = new Node();
		this.first.next.next.next.p = d;

		this.first.next.next.next.next = this.first;
	}
	
	// Imprime a posicao de cada ponto
	public void show(){
		Node temp=this.first;

		do{
			System.out.println(temp.p.toString());
			temp=temp.next;
		}while(!this.first.p.equals(temp.p));
	}

	// Retorna o numero de pontos adicionados
	public int size(){
		if(this.first == null)
			return 0;
		else{
			Node temp=this.first;
			int i=0;
			do{
				i++;
				temp=temp.next;
			}while(!this.first.p.equals(temp.p));
			return i;
		}
	}

	// Retorna a soma das distancias Euclidiana de cada ponto
	public double distance(){
		if(this.first == null)
			return 0.0;
		else{
			Node temp=this.first;
			double count=0.0;
			do{
				count += temp.p.distanceTo(temp.next.p); 
				temp=temp.next;
			}while(!this.first.p.equals(temp.p));
			return count;
		}
	}
	
	// Desenha um linha entre os pontos
	public void draw(){
		Node temp=this.first;
		do{
			temp.p.drawTo(temp.next.p); 
			temp=temp.next;
		}while(!this.first.p.equals(temp.p));
	}
	
	// Insere um ponto no final da lista
	public void insertInOrder(Point P){
		if(this.first==null){
			this.first = new Node();
			this.first.p = P;
			this.first.next = this.first; // Fecha o ciclo mesmo sendo 1 ponto
		}
		else{
			Node temp=this.first;
			while(!this.first.p.equals(temp.next.p)){
				temp=temp.next;
			}
			temp.next = new Node();
			temp.next.next = this.first;
			temp.next.p = P;
		}
	}
	
	public void insertNearest(Point P){
		if(this.first==null){
			this.first = new Node();
			this.first.p = P;
			this.first.next = this.first; // Fecha o ciclo mesmo sendo 1 ponto
		}
		else{
			Node temp = this.first;  // Usado para avancar pelo trajeto
			Node near = this.first;  // Guarda posicao anterior aonde sera adicionado o novo node
			double bestDist = Double.MAX_VALUE; 
			double aux;
			do{
				aux = temp.p.distanceTo(P);  // Calcula distancia do ponto atual
				if(aux<bestDist){  
					near = temp;  
					bestDist = aux;
				}
				temp=temp.next;
			}while(!this.first.p.equals(temp.p));
			temp = near.next;  
			near.next = new Node();
			near.next.next = temp;
			near.next.p = P;
		}
	}
	
	public void insertSmallest(Point P){
		if(this.first==null){
			this.first = new Node();
			this.first.p = P;
			this.first.next = this.first; // Fecha o ciclo mesmo sendo 1 ponto
		}
		else{
			Node temp = this.first;  // Usado para avancar pelo trajeto
			Node near = this.first;  // Guarda posicao anterior aonde sera adicionado o novo node
			double bestDist = Double.MAX_VALUE; 
			double aux1, aux2, aux3;
			do{
				aux1 = temp.p.distanceTo(P);  // Calcula distancia do ponto atual para o antecessor
				aux2 = temp.next.p.distanceTo(P);  // Calcula distancia do ponto atual para o sucessor
				aux3 = temp.p.distanceTo(temp.next.p); // Calcula a distancia do antecessor para o sucessor
				if((aux1+aux2)-aux3<bestDist){  
					near = temp;  
					bestDist = (aux1+aux2)-aux3;
				}
				temp=temp.next;
			}while(!this.first.p.equals(temp.p));
			temp = near.next;  
			near.next = new Node();
			near.next.next = temp;
			near.next.p = P;
		}
	}
	
	
	// main method for testing
 	public static void main(String[] args) {
 		// define 4 points forming a square
 		Point a = new Point(100.0, 100.0);
 		Point b = new Point(500.0, 100.0);
 		Point c = new Point(500.0, 500.0);
 		Point d = new Point(100.0, 500.0);

 		// Set up a Tour with those four points
 		// The constructor should link a->b->c->d->a
 		Tour squareTour = new Tour(a, b, c, d);
 		// Output the Tour
 		squareTour.show();
 		System.out.println(squareTour.size() +" " +squareTour.distance());
 		StdDraw.setXscale(0, 600);
		StdDraw.setYscale(0, 600);

		Point e = new Point(0.0, 0.0);
		squareTour.insertInOrder(e);
		squareTour.show();
		squareTour.draw();
	}
}