/**
 * 
 */
package jigl.image.types;

public enum KernelType {
	/**
	 * Uniform Kernel.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	UNIFORM,
	/**
	 * Sorbel Kernel with X orientation.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>0</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>-2</TD>
	 * <TD>0</TD>
	 * <TD>2</TD>
	 * </TR>
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>0</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	SOBEL_X,
	/**
	 * Sobel Kernel with Y orientation.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>-2</TD>
	 * <TD>-1</TD>
	 * </TR>
	 * <TR>
	 * <TD>0</TD>
	 * <TD>0</TD>
	 * <TD>0</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>2</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	SOBEL_Y,
	/**
	 * Prewitt Kernel with X orientation.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>0</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>0</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>0</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	PREWITT_X,
	/**
	 * Prewitt Kernel with Y orientation.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>-1</TD>
	 * <TD>-1</TD>
	 * </TR>
	 * <TR>
	 * <TD>0</TD>
	 * <TD>0</TD>
	 * <TD>0</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	PREWITT_Y,
	/**
	 * Laplacian Kernel.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>0</TD>
	 * <TD>1</TD>
	 * <TD>0</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>4</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>0</TD>
	 * <TD>1</TD>
	 * <TD>0</TD>
	 * </TR>
	 * </TABLE>
	 */
	LAPLACIAN,
	/**
	 * Laplacian 8 Kernel.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>-8</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	LAPLACIAN_8,
	/**
	 * Unsharpen Kernel.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>0</TD>
	 * <TD>-1</TD>
	 * <TD>0</TD>
	 * </TR>
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>5</TD>
	 * <TD>-1</TD>
	 * </TR>
	 * <TR>
	 * <TD>0</TD>
	 * <TD>-1</TD>
	 * <TD>0</TD>
	 * </TR>
	 * </TABLE>
	 */
	UNSHARP,
	/**
	 * Unsharpen 8 Kernel.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>-1</TD>
	 * <TD>-1</TD>
	 * </TR>
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>9</TD>
	 * <TD>-1</TD>
	 * </TR>
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>-1</TD>
	 * <TD>-1</TD>
	 * </TR>
	 * </TABLE>
	 */
	UNSHARP_8,
	/**
	 * D_xx Kernel.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>1</TD>
	 * <TD>-2</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>-2</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>-2</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	D_XX,
	/**
	 * D_yy Kernel.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * </TR>
	 * <TR>
	 * <TD>-2</TD>
	 * <TD>-2</TD>
	 * <TD>-2</TD>
	 * </TR>
	 * <TR>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	D_YY,
	/**
	 * D_xy Kernel.
	 * <TABLE BORDER CELLPADDING=5 COLS=3 WIDTH="13" >
	 * <TR>
	 * <TD>1</TD>
	 * <TD>0</TD>
	 * <TD>-1</TD>
	 * </TR>
	 * <TR>
	 * <TD>0</TD>
	 * <TD>0</TD>
	 * <TD>0</TD>
	 * </TR>
	 * <TR>
	 * <TD>-1</TD>
	 * <TD>0</TD>
	 * <TD>1</TD>
	 * </TR>
	 * </TABLE>
	 */
	D_XY
}