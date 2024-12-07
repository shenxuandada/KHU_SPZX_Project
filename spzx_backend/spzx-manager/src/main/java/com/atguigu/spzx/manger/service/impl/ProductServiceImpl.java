package com.atguigu.spzx.manger.service.impl;

import com.atguigu.spzx.manger.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manger.mapper.ProductMapper;
import com.atguigu.spzx.manger.mapper.ProductSkuMapper;
import com.atguigu.spzx.manger.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ProductServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/10 下午3:58
 * Version 1.8
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;


    //列表(条件分页查询)
    @Override
    public PageInfo<Product> findByPage(Integer page,
                                        Integer limit,
                                        ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> list = productMapper.findByPage(productDto);
        return new PageInfo<>(list);
    }

    //添加
    @Override
    public void save(Product product) {
        //1 保存商品基本薪 product表
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);

        //2 获取商品sku列表集合,保存sku信息,product_sku表
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i = 0; i < productSkuList.size(); i++) {
            ProductSku productSku = productSkuList.get(i);

            //商品编号
            productSku.setSkuCode(product.getId() + "_" + i);
            //商品ID
            productSku.setProductId(product.getId());
            //skuName
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            //设置销量
            productSku.setSaleNum(0);
            productSku.setStatus(0);
            productSkuMapper.save(productSku);
        }

        //3 保存商品详细数据 product_details表
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);

    }

    //根据商品id查询商品信息
    @Override
    public Product getById(Long id) {
        //1 根据id查询商品基本信息 product
        Product product = productMapper.findProductById(id);

        //2 根据id查询商品sku信息列表 product_sku
        List<ProductSku>productSkuList = productSkuMapper.findProductSkuByProductId(id);
        product.setProductSkuList(productSkuList);

        //3 根据id删除商品详细数据 product_details
        ProductDetails productDetails= productDetailsMapper.findProductDetailsById(id);
        String imageUrls= productDetails.getImageUrls();
        product.setDetailsImageUrls(imageUrls);

        return product;
    }

    //保存修改数据
    @Override
    public void update(Product product) {
        //修改products
        productMapper.updateById(product);

        //修改product_sku
        List<ProductSku>productSkuList = product.getProductSkuList();
        productSkuList.forEach(productSku -> {
            productSkuMapper.updateById(productSku);
        });

        //修改product_details
        String detailsImageUrls= product.getDetailsImageUrls();
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(product.getId());
        productDetails.setImageUrls(detailsImageUrls);
        productDetailsMapper.updateById(productDetails);
    }

    //删除
    @Override
    public void deleteById(Long id) {
        //1 根据商品id删除product表
        productMapper.deleteById(id);

        //2 根据商品id删除product_sku表
        productSkuMapper.deleteByProductId(id);

        //3 根据商品id删除product_details表
        productDetailsMapper.deleteByProductId(id);

    }

    //审核
    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if(auditStatus == 1) {
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        } else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateById(product);
    }

    //商品上下架
    @Override
    public void updateStatus(Long id,Integer status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1) {
            product.setStatus(1);
        } else {
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}
