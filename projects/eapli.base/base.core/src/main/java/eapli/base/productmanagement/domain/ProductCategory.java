/*
 * Copyright (c) 2021-2022 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package eapli.base.productmanagement.domain;

import com.sun.istack.NotNull;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.Description;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductCategory implements AggregateRoot<ProductCategoryCode>{

    @Id
    private ProductCategoryCode code;

    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private ProductCategoryDescription description;

    protected ProductCategory(){
        // for ORM
    }

    public ProductCategory(@NotNull ProductCategoryCode code, @NotNull ProductCategoryDescription description){
        if(code == null)
            throw new IllegalArgumentException("Product Category Code cannot be null");

        if(description == null)
            throw new IllegalArgumentException("Product Category Description cannot be null");

        this.code = code;
        this.description = description;
    }

    public ProductCategory(@NotNull ProductCategoryCode code){
        if(code == null)
            throw new IllegalArgumentException("Product Category Code cannot be null");

        this.code = code;
        this.description = null;
    }

    public ProductCategoryCode code(){
        return this.code;
    }

    public ProductCategoryDescription description(){
        return this.description;
    }

    @Override
    public boolean sameAs(Object other) {
        return  DomainEntities.areEqual(this, other);
    }

    @Override
    public int compareTo(ProductCategoryCode other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public ProductCategoryCode identity() {
        return this.code;
    }

    @Override
    public boolean hasIdentity(ProductCategoryCode id) {
        return AggregateRoot.super.hasIdentity(id);
    }
}
