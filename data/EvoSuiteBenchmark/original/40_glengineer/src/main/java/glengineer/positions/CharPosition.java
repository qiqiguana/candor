package glengineer.positions;

/**
 * �����, ��������������� ���� ���������
 * ��� ������ � ��������� ��������� � �������.
 */
public class CharPosition
{
	public final int x, y;
	
	public CharPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(CharPosition p) {
		return x == p.x && y == p.y;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
