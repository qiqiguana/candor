/* This file is part of the JIGL Java Image and Graphics Library.
 * 
 * Copyright 1999 Brigham Young University.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * A copy of the GNU Lesser General Public License is contained in
 * the 'licenses' directory accompanying this distribution.
 */
package jigl.image.warp;

import jigl.image.Image;
import jigl.image.InterpolationMethod;
import jigl.image.exceptions.ImageNotSupportedException;
import jigl.image.ops.SimpleOperator;
import jigl.image.types.ColorImage;
import jigl.image.types.GrayImage;
import jigl.image.types.InterpolatedColorImage;
import jigl.image.types.InterpolatedGrayImage;
import jigl.image.types.InterpolatedRealColorImage;
import jigl.image.types.InterpolatedRealGrayImage;
import jigl.image.types.RealColorImage;
import jigl.image.types.RealGrayImage;

/**Mapper applies a transformation (PointMapper) to an entire image.*/
public class Mapper extends SimpleOperator
{
//	protected Image image;
//	protected InterpolatedImage interpImg;
	/**Interpolation methods: neighbor, linear or cubic*/
	private InterpolationMethod m_interpMethod;
	/**Pointmapper object for transformation*/
	private PointMapper m_mapper;

	/**Interpolation method types 1*/
	public static final int NEIGHBOR = 0;
	/**Interpolation method types 2*/
	public static final int LINEAR = 1;
	/**Interpolation method types 3*/
	public static final int CUBIC = 2;

	/**Backgroung color*/
	protected float m_defColor[] = new float[3];

	/** Constructs a Mapper object from a PointMapper object and an interpolation method.
	@param img the image to apply the transformation
	@param interpolationMethod the way to interpolate (0=NEIGHBOR 1=LINEAR 2=CUBIC)*/
  	public Mapper(PointMapper mapper, InterpolationMethod interpolationMethod) throws ImageNotSupportedException
	{
		m_mapper = mapper;
		m_interpMethod = interpolationMethod;
		m_defColor[0] = m_defColor[1] = m_defColor[2] = 0;
	}

	/** set the color that will be use in apply() when out of bounds of the interpolated image.
	@param color color to be used as default color.  color will be used to the extent needed by the image type.  For example,
	a GrayImage will return (int)color[0] for the default color, and RealColorImage will use the first three elements of
	color.  If not enough elements are in the array, the last color will be repeated as needed.*/
	public void setDefaultColor(float[] color)
	{
		int i;

		if (color.length == 0)
		{
			m_defColor[0] = m_defColor[1] = m_defColor[2] = 0;
			return;
		}

		for (i = 0; i < color.length && i < 3; i++)
		{
			m_defColor[i] = color[i];
		}

		while (i < 3)
		{
			m_defColor[i] = m_defColor[i-1];
			i++;
		}
	}

  	/** Applies the transformation to <code>image</code> through the PointMapper.
	<code>image</code> is not modified.
	@param image GrayImage to map.
	@return a GrayImage object.*/
	protected Image apply(GrayImage image)
	{
		InterpolatedGrayImage interpImage = new InterpolatedGrayImage(image);
		interpImage.setInterpolationMethod(m_interpMethod);
		GrayImage img = new GrayImage(image.X(), image.Y());
		int value = 0;
		float[] x1 = new float[1];
		float[] y1 = new float[1];
		for (int x = 0; x < image.X(); x++)
		{
			for (int y = 0; y < image.Y(); y++)
			{
				x1[0] = x;
				y1[0] = y;
				m_mapper.inverseTransform(x1,y1);//get the orignal position before transforming
				try{
					value = interpImage.interp(x1[0],y1[0]);//get the value in that position
					img.set(x,y,value);
				}
				catch (Exception e) {img.set(x,y,(int)m_defColor[0]);}
			}
		}
		return img;
	}

  /** Applies the transformation to <code>image</code> through the PointMapper. <code>image</code> is not modified.
	@param image RealGrayImage to map.
	@return RealGrayImage.*/
	protected Image apply(RealGrayImage image)
	{
		InterpolatedRealGrayImage interpImage = new InterpolatedRealGrayImage(image);
		interpImage.setInterpolationMethod(m_interpMethod);
		RealGrayImage img = new RealGrayImage(image.X(),image.Y());
		float value = 0;
		float[] x1 = new float[1];
		float[] y1 = new float[1];
		for (int x = 0; x < image.X(); x++)
			for (int y = 0; y < image.Y(); y++){
				x1[0] = x;
				y1[0] = y;
				m_mapper.inverseTransform(x1,y1);
				try{
					value = interpImage.interp(x1[0],y1[0]);
					img.set(x,y,value);
				}
				catch (Exception e) {img.set(x,y,m_defColor[0]);}
			}
			return img;
	}

  /** Applies the transformation to <code>image</code> through the PointMapper. <code>image</code> is not modified.
	@param image ColorImage to map.
	@return ColorImage.*/
	protected Image apply(ColorImage image)
	{
		InterpolatedColorImage interpImage = new InterpolatedColorImage(image);
		interpImage.setInterpolationMethod(m_interpMethod);
		ColorImage img = new ColorImage(image.X(),image.Y());
		Integer[] value = null;
		float[] x1 = new float[1];
		float[] y1 = new float[1];
		for (int x = 0; x < image.X(); x++)
		{
			for (int y = 0; y < image.Y(); y++)
			{
				x1[0] = x;
				y1[0] = y;
				m_mapper.inverseTransform(x1,y1);
				try{
					value = interpImage.interp(x1[0],y1[0]);
					img.set(x,y,value);
				}
				catch (Exception e) {
					GrayImage temp = img.plane(0);
					temp.set(x,y,(int)m_defColor[0]);
					temp = img.plane(1);
					temp.set(x,y,(int)m_defColor[1]);
					temp = img.plane(2);
					temp.set(x,y,(int)m_defColor[2]);}
			}
		}
		return img;
	}

  /** Applies the transformation to <code>image</code> through the PointMapper. <code>image</code> is not modified.
	@param image RealColorImage to map.
	@return RealColorImage.*/
	protected Image apply(RealColorImage image)
	{
		InterpolatedRealColorImage interpImage = new InterpolatedRealColorImage(image);
		interpImage.setInterpolationMethod(m_interpMethod);
		RealColorImage img = new RealColorImage(image);
		Float[] value = null;
		float[] x1 = new float[1];
		float[] y1 = new float[1];
		for (int x = 0; x < image.X(); x++)
		{
			for (int y = 0; y < image.Y(); y++)
			{
				x1[0] = x;
				y1[0] = y;
				m_mapper.inverseTransform(x1,y1);
				try{
					value = interpImage.interp(x1[0],y1[0]);
					img.set(x,y,value);
				}
				catch (Exception e) {
					RealGrayImage temp = img.plane(0);
					temp.set(x,y,m_defColor[0]);
					temp = img.plane(1);
					temp.set(x,y,m_defColor[0]);
					temp = img.plane(2);
					temp.set(x,y,m_defColor[0]);}
			}
		}
		return img;
	}
}

