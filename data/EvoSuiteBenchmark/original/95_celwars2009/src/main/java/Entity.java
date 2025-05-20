import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Entity 
{
	private double x, y, vx,vy,fx,fy, vs;
	private double size;
	private int team = -1;
	private HashMap<String, Double> properties;
	//private ArrayList<FOGSystem> systems;
	private Random gen = new Random();
	private ArrayList<Entity> nearby = new ArrayList<Entity>();
	private int livingNearby = 0;
	
	public Entity(double x, double y, int team )
	{
		this.properties = new HashMap<String, Double>();
		this.properties.put("age",1.0); 
		if(team >=0)
			this.properties.put("id", new Double(gen.nextInt(99999999)));
		else
			this.properties.put("id", new Double(-gen.nextInt(99999999)));
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
		vs = 0;
		this.size = 15;
		this.team = team;
//		this.properties.put("team",team); // -1 == food -2 == obstacle

	}
	public Entity(double x, double y, int team, Entity parent )
	{
		this.properties = new HashMap<String, Double>();
		this.properties.putAll(parent.properties);
		this.properties.put("age",1.0); 
		if(team >=0)
			this.properties.put("id", new Double(gen.nextInt(99999999)));
		else
			this.properties.put("id", new Double(-gen.nextInt(99999999)));
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
		vs = 0;
		this.size = 15;
		this.team = team;
//		this.properties.put("team",team); // -1 == food -2 == obstacle

	}


	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public int getTeam() {
		return team;
	}


	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Random getGen() {
		return gen;
	}

	public HashMap<String, Double> getProperties() {
		return properties;
	}

	double dist(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
	}
	/*
	 * 		r(t + ?t) = r(t) + v(t) ?t+ (1/2)a(t) ?t2			Position update
	 		v(t +?t/2) = v(t) + (1/2)a(t)?t						Half Velocity update
	 		a(t + ?t) = -(1/m)    V(r(t + ?t))					Force update
	 		v(t + ?t) = v(t + ?t/2) + (1/2)a(t + ?t) ?t		Full velocity update

	 * 
	 */
	public void integrate(Server s) 
	{
		if(team > -1 && s.getPlayers().size() > team && s.getPlayers().get(team) != null && s.getPlayers().get(team).clientSocket != null && !s.getPlayers().get(team).clientSocket.isConnected())
		{
			s.getPlayers().get(team).getP().setNumCells(s.getPlayers().get(team).getP().getNumCells()-1);
			System.err.println("removed a left-over entity");
			s.getEntities().remove(this);
			return;
		}
		
		if(gen.nextInt(10000) == 0 && team > -1)
		{
			//random evolution
			String[] list = {"Simple Flagella","Senescence Seggregation","Toxic Apoptosis","Antibiotic Receptors","Group Growth","Aggression","Cellular Walls","Lysozymes","Antibiotic Resistance","Telomerase","Group Attraction","Complex Flagella","Photosynthesis"};
			if(gen.nextBoolean())
			{
				String adding = list[gen.nextInt(list.length)];
				properties.put(adding,1.0);
				s.sendMessage("Message 255 One of your cells has aquired "+adding+" by random mutation!",team);
			}
			else
			{
				String adding = list[gen.nextInt(list.length)];
				if(properties.get(adding) != null)
				{
					properties.remove(adding);
					s.sendMessage("Message 16581375 One of your cells has lost "+adding+" by random mutation!",team);
				}

			}
		}
		//position update
		double oldx = x;
		double oldy = y;
		double olds = size;
		if(team > -1 || team == -5)
		{
			if(x+vx+((1.0/2.0)*fx)/size < 0 || x+vx+((1.0/2.0)*fx)/size > s.getWorldSize())
			{
				
				vx += (s.getWorldSize()/2-x)/s.getWorldSize();
			}
			if(y+vy+((1.0/2.0)*fy)/size < 0 || y+vy+((1.0/2.0)*fy)/size > s.getWorldSize())
			{
				
				vy += (s.getWorldSize()/2-y)/s.getWorldSize();
			}
		}

		x+= vx+((1.0/2.0)*fx)/(size*size);
		y+= vy+((1.0/2.0)*fy)/(size*size);		
		size += vs; 
		//half velocity update
		vx +=((1.0/2.0)*fx)/(size*size) ;
		vy += ((1.0/2.0)*fy)/(size*size);

		if(getTeam() > -1)
		{
			//x += gen.nextDouble()*0.8 - gen.nextDouble()*0.8 ;
			//y += gen.nextDouble()*0.8 - gen.nextDouble()*0.8 ;
			if(properties.get("Simple Flagella") != null|| properties.get("Complex Flagella") != null && gen.nextInt(100) < 4)
			{
				vx += gen.nextDouble()*0.3 - gen.nextDouble()*0.3 ;
				vy += gen.nextDouble()*0.3 - gen.nextDouble()*0.3 ;
			}
			
			if(properties.get("Group Growth") != null)
				size += livingNearby*0.0003;
				size += livingNearby*0.0001;
			if(properties.get("Photosynthesis") != null)
				vs+= 0.004;
			vs += -(1.0/3.0)*(vs-(-0.0003-s.getPlayers().get(team).getP().getScore()*s.getPlayers().get(team).getP().getScore()*0.0000001));
		}

		if(getTeam() ==  -1)
		{
			vs += (-1.0/4.0)*(vs-0.005);
		}
		if(getTeam() ==  -3) //poison
		{
			vs += -(1.0/2.0)*(vs+0.01);
		}
		if(getTeam() ==  -4) //pheremone
		{
			vs += -(1.0/2.0)*(vs+0.03);
		}
		
		
		//force update
		if(team != -3)
		{
			fx = 0;
			fy = 0;
		}
		else
		{
			fx = fx*0.9;
			fy = fy*0.9;			
		}

		{
			if(s.getTime() % 10 == 0)
			{
				nearby.clear();
				livingNearby =0;
				for(int i = 0; i < s.getEntities().size(); i++)
				{
					Entity e = s.getEntities().get(i);
					if(e.team > -1)
					{
						if(nearby.size() < 20 &&e.x != x && e.y != y && dist(x,y, e.x, e.y) < 200);
						{
							nearby.add(e);
							livingNearby++;
						}
					}
					else
					{
						if(nearby.size() < 20 && !e.equals(this) && dist(x,y, e.x, e.y) < 50);
						{
							nearby.add(e);
						}
		
					}
				}
			}
			for(int i = 0; i < nearby.size(); i++)
			{
				double dx = nearby.get(i).getX()-x;
				double dy = nearby.get(i).getY()-y;
				double mag = Math.sqrt(dx*dx+dy*dy);
				double distance2 = (dx*dx)+(dy*dy);
				double distance1 = Math.sqrt(distance2);
	//			double kenergy = (vx*vx+vy*vy)*size;
				if(distance1 > (nearby.get(i).getSize()/4+getSize()/4))
				{
					double two = ((nearby.get(i).getSize()/2+size/2)*(nearby.get(i).getSize()/2+size/2))/distance2;
	
					//attracted to food
					//attracted to others of same team
					//repelled by obstacles
					//repelled by poison
					//attracted to pheremones
					if(nearby.get(i).getTeam() == -4 && (getTeam() > -1)&& (properties.get("Simple Flagella") != null || properties.get("Complex Flagella") != null)&& distance1 < 100)
					{
						//pheremone
						double ex = ((nearby.get(i).size)*1.6)/(distance1/(size/2+nearby.get(i).size/2));
						fx += ex*dx;
						fy += ex*dy;
					}
					
					if(nearby.get(i).getTeam() == -1 && getTeam() > -1)
					{
						//food
						//double six= (two*two*two);
						if(properties.get("Food Receptors") != null&& (properties.get("Simple Flagella") != null || properties.get("Complex Flagella") != null) && distance1 > 0.8*(size/2+nearby.get(i).getSize()/2))
						{
							double ex =  (nearby.get(i).size*10)* ((2*two)/distance1);
							fx += ex*dx;
							fy += ex*dy;
						}
						if(dist(x,y,nearby.get(i).x, nearby.get(i).y)*0.9 <= size/2 + nearby.get(i).size/2 )
						{
							vx -= dx/10;
							vy -= dy/10;
							fx += dx/4;
							fy += dy/4;
							//System.err.println("Cell hit food: vx "+vx+" vy "+vy+" fx "+fx+" fy "+fy);
						}
						
					}
					if(nearby.get(i).getTeam() == -3 && getTeam() > -1)
					{
						//poison
						if(properties.get("Antibiotic Receptors") != null&& (properties.get("Simple Flagella") != null || properties.get("Complex Flagella") != null)&& distance1 > 0.8*(size/2+nearby.get(i).getSize()/2))
						{
							double ex =  -(nearby.get(i).size*10)* ((2*two)/distance1);
							fx += ex*dx;
							fy += ex*dy;
						}
						if(dist(x,y,nearby.get(i).x, nearby.get(i).y)*0.9 <= (size/2 + nearby.get(i).size/2) )
						{
							vx = -(dx)/9;
							vy = -dy/9;
						}
						
					}
					if(team > -1 && nearby.get(i).getTeam() == getTeam() && !nearby.get(i).equals(this) )
					{
						//other team members
						if(properties.get("Group Attraction") != null&& (properties.get("Simple Flagella") != null || properties.get("Complex Flagella") != null)&& distance1 > 0.8*(size/2+nearby.get(i).getSize()/2))
						{

							double ex = 0; 

							if(properties.get("age") > 104 && properties.get("Senescence segregation") != null)
							{
								ex = -(nearby.get(i).size*10)* ((2*two)/distance1);
							}
							else
							{
								ex = (nearby.get(i).size*10)* ((2*two)/distance1);
							}
							fx += ex*dx;
							fy += ex*dy;
						}
						if(dist(x,y,nearby.get(i).x, nearby.get(i).y)*0.9 <= (size/2 + nearby.get(i).size/2) )
						{
							double eight= (two*two*two*two);
							double ex = -(0.1)*(8*eight)/distance1;
							vx += ex*dx;
							vy += ex*dy;
						}
						
						if(gen.nextInt(1000) == 0)
						{
							Iterator<String> itr = nearby.get(i).properties.keySet().iterator();
							while(itr.hasNext())
							{
								String next = itr.next();
								if(properties.get(next) == null && gen.nextBoolean())
								{
									properties.put(next, 1.0);
									s.sendMessage("Message 255 One of your cells has aquired "+next+" from a nearby cell!",team);
								}
							}
						}
					}
					if((team > -1||   team == -5) && nearby.get(i).getTeam() > -1 && nearby.get(i).getTeam() != getTeam() && !(nearby.get(i).properties.get("id") == properties.get("id")) && distance1 < 100  )
					{
						//rival blobs
						if((properties.get("Release Antibiotic") != null && gen.nextDouble()*size > 20) || team == -5)
						{
							if(gen.nextInt(25)==0)
							{
								Entity poison = new Entity(x+dx/mag*size/2,y+dy/mag*size/2, -3);
								poison.setSize(gen.nextDouble()*3+8);
								poison.fx = -(dx)*2;
								poison.fy = -(dy)*2;
								poison.vx = dx/mag*size/2;
								poison.vy = dy/mag*size/2;
								s.getEntities().add(poison);
								doSend(poison,s);
								nearby.add(poison);
								s.addSound("plop3.wav "+(int)x+" "+(int)y+";");
							}
						}
						if(nearby.get(i).team > -1 && ((properties.get("Aggression") != null&& (properties.get("Simple Flagella") != null || properties.get("Complex Flagella") != null)&& distance1 > 0.8*(size/2+nearby.get(i).getSize()/2))))
						{
							double ex =  0.013*(size-nearby.get(i).size)* (distance1/(size/2+nearby.get(i).size/2));
							fx += ex*dx;
							fy += ex*dy;
						}
						if(nearby.get(i).team > -1 && (team == -5 && distance1 > 0.8*(size/2+nearby.get(i).getSize()/2)))
						{
							double ex =  0.005*(nearby.get(i).size)* (distance1/(size/2+nearby.get(i).size/2));
							fx += ex*dx;
							fy += ex*dy;
						}
						
						if(dist(x,y,nearby.get(i).x, nearby.get(i).y)*0.9 <= (size/2 + nearby.get(i).size/2) )
						{
							vx = -(dx)/9;
							vy = -dy/9;
						}
						//System.err.println("team interaction "+ex+" dx "+dx+" dy "+dy);
					}
					if(nearby.get(i).getTeam() == -2 && getTeam() > -1)
					{
						//obstacle
						if(dist(x,y,nearby.get(i).x, nearby.get(i).y)*0.95 <= (size/2 + nearby.get(i).size/2) )
						{
							vx = -(dx)/8;
							vy = -dy/8;
						}
						if(properties.get("Enzymatic Revolution") != null)
						{
							nearby.get(i).vs -= size*0.0004;
							this.vs += nearby.get(i).size*0.0003;
						}
						//System.err.println("obstabce interaction "+ex+" dx "+dx+" dy "+dy);
					}
	
				}
			}
		}
		//full velocity update
		vx += ((1.0/2.0)*fx)/(size*size);
		vy += + ((1.0/2.0)*fy)/(size*size);
		double fmag = Math.sqrt(fx*fx+fy*fy);
		double vmag = Math.sqrt(vx*vx+vy*vy);
		if(fmag > 2)
		{
			fx = (fx/fmag)*2;
			fy = (fy/fmag)*2;
		}
		if(vmag > 5)
		{
			vx = (vx/vmag)*5;
			vy = (vy/vmag)*5;
		}
		//also fix velocity based on temperature
		if(vx*vy != 0)
		{
			double Ct = Math.sqrt(1-(1.0/10.0)*(1-20.0/(size*size*vx*vx+size*size*vy*vy)));
			if(properties.get("Complex Flagella") != null)
			{
				Ct = Math.sqrt(1-(1.0/20.0)*(1-110.0/(size*size*vx*vx+size*size*vy*vy)));
			}
			else if(properties.get("Simple Flagella") != null)
			{
				Ct = Math.sqrt(1-(1.0/20.0)*(1-70.0/(size*size*vx*vx+size*size*vy*vy)));
			}

			vx = vx*Ct;
			vy = vy*Ct;
		}
		//also go through nearby and update things based on current conditions.
		if(team > -1)
		{
			for(int i = 0; i < nearby.size(); i++)
			{
				Entity e = nearby.get(i);
				double d = dist(x,y, e.x, e.y);
				if(d < (size/1.5+ e.getSize()/1.5 + 4) )
				{
					if(e.getTeam() == -1)
					{
						//food
						if(properties.get("Enzymatic Revolution") != null)
							vs += 0.0009*e.size;
						else
							vs +=0.0003*e.size;
						e.vs -= 0.0004*size;
						e.integrate(s);
					}
					if(e.getTeam() == -3)
					{
						//poison
						if(properties.get("Antibiotic Resistance") != null)
							vs -= 0.003*e.size;
						else
							vs -= 0.01*e.size;
						if(properties.get("Enzymatic Revolution") != null)
						{
							nearby.get(i).vs -= size*0.0004;
							this.vs += nearby.get(i).size*0.003;
						}
						
					}
					if((e.getTeam() > -1) && e.getTeam() != getTeam())
					{
						//next to an opponent cell
						double factor = 0.01;
						if(e.getProperties().get("Cell Wall") != null)
							factor-= 0.03;
						if(this.getProperties().get("Lysozymes") != null)
							factor+= 0.028;
						if(team == -5) factor+=0.05;
						e.vs -= size*factor;
						if(properties.get("Enzymatic Revolution") != null)
						{
							this.vs += nearby.get(i).size*0.0004;
						}
					}
					if(properties.get("Enzymatic Revolution") != null && e.getTeam() == -5)
					{
						e.vs -= size*0.0005; //die terminator bitch!
					}
				}
			}
			
			if(this.properties.get("Telomerase") == null|| team == -5)
				properties.put("age", properties.get("age")+0.03);
			else
				properties.put("age", properties.get("age")+0.012);
			if(gen.nextDouble()*getProperties().get("age") > 100)
			{
				//death
				vs -= 1;

			}
			//System.err.println("Entity at "+x+" "+y+" size "+size+" vx "+vx+" vy "+vy+" vs "+vs+" fx "+fx+" fy "+fy );

		}
		if(team > -1 && gen.nextInt((int)size + 1) > 25)
		{
			//split!!!
			double x1 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + x;
			double x2 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + x;
			double y1 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + y;
			double y2 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + y;
			this.x = x1;
			this.y = y1;
			if(properties.get("Triplication")!= null)
			{
				double x3 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + x;
				double y3 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + y;
				Entity daughter2 = new Entity(x3,y3, this.getTeam(),this);
				daughter2.setSize(this.size/2);
				s.getEntities().add(daughter2);
				doSend(daughter2,s);
				s.getPlayers().get(team).getP().setPower(s.getPlayers().get(team).getP().getPower()+5);
				s.getPlayers().get(team).getP().setNumCells(s.getPlayers().get(team).getP().getNumCells()+1);
			}
			Entity daughter = new Entity(x2,y2, this.getTeam(),this);
			daughter.setSize(this.size/2);
			s.getEntities().add(daughter);
			doSend(daughter,s);
			this.size = size/2;
			s.getPlayers().get(team).getP().setPower(s.getPlayers().get(team).getP().getPower()+10);
			s.addSound("split.wav "+(int)x+" "+(int)y+";");
			s.getPlayers().get(team).getP().setNumCells(s.getPlayers().get(team).getP().getNumCells()+1);
		}
		if(team == -1 && gen.nextInt((int)size + 1) > 50)
		{
			//split!!!
			double x1 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + x;
			double x2 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + x;
			double y1 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + y;
			double y2 = gen.nextDouble()*(size/2) -gen.nextDouble()*(size/2)   + y;
			this.x = x1;
			this.y = y1;
			Entity daughter = new Entity(x2,y2, this.getTeam());
			daughter.nearby.addAll(this.nearby);
			daughter.setSize(this.size/2);
			s.getEntities().add(daughter);
			doSend(daughter,s);
			this.size = size/2;
		}
		if(this.size <= 8)
		{
			doSend(this, s);
			s.addSound("death.wav "+(int)x+" "+(int)y+";");
			if(properties.get("Toxic Apoptosis") != null || gen.nextInt(100) < 8)
			{
				Entity poison = new Entity(x,y, -3);
				poison.setSize(gen.nextDouble()*10+10);
				s.getEntities().add(poison);
				doSend(poison,s);
			}
			if(team > -1)
			{
				s.getPlayers().get(team).getP().setNumCells(s.getPlayers().get(team).getP().getNumCells()-1);
				if(s.getPlayers().get(team).getP().getNumCells() == 0)
				{
					s.sendMessage("Message2 255 All of your cells have died. You lost!",team);
					s.sendMessage("MP3 lose.mp3",team);
					s.sendMessage("Sound lose.wav "+(int)x+" "+(int)y,team);
					System.err.println("Player "+team+" died with score of "+s.getPlayers().get(team).getP().getScore());
					//s.getPlayers().remove(team);
					s.sendToAll("Message 2555 Player "+team+" has lost!");
					s.getPlayers().get(team).disconnect();
				}
			}
			s.getEntities().remove(this);
			return;
		}
		if(Math.abs((int)oldx - (int)x) > 0 || Math.abs((int)oldy - (int)y) > 0 || Math.abs((int)olds - (int)size) > 0 )
		{
			doSend(this,s);
		}
		else
			dontSend(this,s);
	}

	private void doSend(Entity e, Server s)
	{
		if(!s.getSend().contains(e))
			s.getSend().add(e);
	}
	private void dontSend(Entity e, Server s)
	{
		if(s.getSend().contains(e))
			s.getSend().remove(e);
	}


	public void setProperties(HashMap<String, Double> properties) {
		this.properties = properties;
	}

	
}

