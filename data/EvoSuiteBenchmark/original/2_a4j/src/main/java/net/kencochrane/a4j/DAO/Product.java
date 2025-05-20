/*
Copyright (c) 2003, Ken Cochrane
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted
provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

    * Neither the name of Ken Cochrane nor the names of its contributors may be used to endorse
    or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package net.kencochrane.a4j.DAO;

import com.wutka.jox.JOXBeanInputStream;
import net.kencochrane.a4j.beans.*;
import net.kencochrane.a4j.file.FileUtil;

import java.io.FileInputStream;
import java.util.ArrayList;

//import org.apache.log4j.Logger;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 24, 2003
 * Time: 6:21:00 PM
 *
 *
 */
public class Product {
    //  Logger log = Logger.getLogger(this.getClass());

    /**
     *
     * @param asin
     * @param offer
     * @param page
     * @return
     */
    public FullProduct getProduct(String asin, String offer, String page) {
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        FullProduct fullProduct = new FullProduct();
        ProductDetails productDetails = new ProductDetails();
        ProductDetails accessoryProductDetails = new ProductDetails();
        Accessories accessories = new Accessories();
        ArrayList accessoryArray = new ArrayList();
        ArrayList detailsArray = new ArrayList();
        MiniProduct miniProduct = new MiniProduct();
        Search search = new Search();
        try {
            //       log.debug("Get product");
            FileInputStream fileIn = fileUtil.fetchASINFile(asin, "heavy", offer, page);

            if (fileIn != null) {
                //          log.debug("Get product fileIn not null");
                joxIn = new JOXBeanInputStream(fileIn);
                ProductInfo productInfoBean = (ProductInfo) joxIn.readObject(ProductInfo.class);
                joxIn.close();
                fileIn.close();
                if (productInfoBean != null && productInfoBean.getDetails().length > 0) {
                    //we are only searching for one item so it is always 0 in the array
                    productDetails = productInfoBean.getDetails()[0];
                    fullProduct.setDetails(productDetails);
                    //get accessories
                    //          log.debug("Get Accessories");
                    accessories = productDetails.getAccessories();
                    accessoryArray = accessories.getAccessoryArray();
                    if (accessoryArray != null && accessoryArray.size() > 0) {
                        //            log.debug("Get Accessories not null");
                        fileIn = fileUtil.fetchAccessories(asin, accessoryArray);
                        if (fileIn != null) {
                            //                log.debug("Get Accessories - filein not null");
                            joxIn = new JOXBeanInputStream(fileIn);
                            productInfoBean = (ProductInfo) joxIn.readObject(ProductInfo.class);
                            joxIn.close();
                            fileIn.close();
                            if (productInfoBean != null) {
                                if (productInfoBean.getProductsArrayList().size() > 0) {
                                    detailsArray = productInfoBean.getProductsArrayList();
                                    for (int x = 0; x < detailsArray.size(); x++) {
                                        accessoryProductDetails = (ProductDetails) detailsArray.get(x);
                                        if (accessoryProductDetails != null) {
                                            miniProduct = new MiniProduct();
                                            miniProduct.setAsin(accessoryProductDetails.getAsin());
                                            miniProduct.setImageURL(accessoryProductDetails.getImageUrlSmall());
                                            miniProduct.setManufacturer(accessoryProductDetails.getManufacturer());
                                            miniProduct.setName(accessoryProductDetails.getProductName());
                                            miniProduct.setPrice(accessoryProductDetails.getOurPrice());
                                            fullProduct.addAccessory(miniProduct);
                                        }
                                    }

                                }
                            }
                        }
                    }

                    //           log.debug("get SimilarItems");
                    productInfoBean = search.SimilaritesSearch(asin, "1");
                    if (productInfoBean != null) {
                        //               log.debug("bean not null");
                        if (productInfoBean.getProductsArrayList() != null && productInfoBean.getProductsArrayList().size() > 0) {
                            //                       log.debug("ArrayList size is > 0");
                            detailsArray = productInfoBean.getProductsArrayList();
                            for (int x = 0; x < detailsArray.size(); x++) {
                                accessoryProductDetails = (ProductDetails) detailsArray.get(x);
                                if (accessoryProductDetails != null) {
                                    miniProduct = new MiniProduct();
                                    miniProduct.setAsin(accessoryProductDetails.getAsin());
                                    miniProduct.setImageURL(accessoryProductDetails.getImageUrlSmall());
                                    miniProduct.setManufacturer(accessoryProductDetails.getManufacturer());
                                    miniProduct.setName(accessoryProductDetails.getProductName());
                                    miniProduct.setPrice(accessoryProductDetails.getOurPrice());
                                    fullProduct.addSimilarItem(miniProduct);
                                }
                            }

                        }
                    }
                }

            } else {
                //           log.debug("Error no ASIN file from Input");
                return null;
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return fullProduct;
    }


}
