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
import net.kencochrane.a4j.beans.ShoppingCart;
import net.kencochrane.a4j.beans.ShoppingCartResponse;
import net.kencochrane.a4j.data.Query;
import net.kencochrane.a4j.file.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//import org.apache.log4j.Logger;

/**
 * http://www.KenCochrane.net
 * Ken Cochrane
 * Date: May 24, 2003
 * Time: 12:55:37 PM
 *
 *
 */
public class Cart {
    //Logger log = Logger.getLogger(this.getClass());

    public ShoppingCart AddtoCart(String asin, String quantity) {
        //   log.debug("In Cart");
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;

        String queryString = query.AddtoCart(asin, quantity);
        //    log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);

        if (file != null) {
            //     log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //        log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //         log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }

    public ShoppingCart addToExistingCart(String cartId, String hmac, String asin, String quantity) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        String queryString = query.AddToExistingCart(asin, quantity, cartId, hmac);
        //    log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //      log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //        log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //          log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }


    public ShoppingCart clearCart(String hmac, String cartId) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        String queryString = query.ClearCart(cartId, hmac);
        //    log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //       log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //        log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //          log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }

    public ShoppingCart modifyCart(String hmac, String cartId, String itemId, String quantity) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        if (quantity.equalsIgnoreCase("0")) {
            //if they want to set quantity to 0 then remove from cart.
            return RemoveFromCart(hmac, cartId, itemId);
        }
        String queryString = query.ModifyCart(itemId, quantity, cartId, hmac);
        //    log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //        log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //          log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //           log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }

    public ShoppingCart GetItemsFromCart(String hmac, String cartId) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        String queryString = query.GetItemsFromCart(cartId, hmac);
        //     log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //         log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //          log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //           log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }

    public ShoppingCart RemoveFromCart(String hmac, String cartId, String itemId) {
        Query query = new Query();
        FileUtil fileUtil = new FileUtil();
        JOXBeanInputStream joxIn = null;
        ShoppingCart shoppingCart = null;
        String queryString = query.RemoveFromCart(itemId, cartId, hmac);
        //     log.debug("queryString = " + queryString);
        File file = fileUtil.downloadCart(queryString);
        if (file != null) {
            //         log.debug("file not null");
            try {
                FileInputStream fin = new FileInputStream(file);
                joxIn = new JOXBeanInputStream(fin);
                ShoppingCartResponse cartBean = (ShoppingCartResponse) joxIn.readObject(ShoppingCartResponse.class);
                joxIn.close();
                fin.close();

                if (cartBean != null && cartBean.getShoppingCart() != null) {
                    shoppingCart = cartBean.getShoppingCart();
                } else {
                    System.out.println("CartBean is null !");
                }

            } catch (FileNotFoundException fnfe) {
                //error
                //           log.error(fnfe.toString());
                fnfe.printStackTrace();
            } catch (IOException e) {
                //             log.error(e.toString());
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }
}
