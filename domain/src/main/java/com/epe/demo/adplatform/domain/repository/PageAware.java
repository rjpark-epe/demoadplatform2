
package com.epe.demo.adplatform.domain.repository;

import org.springframework.data.domain.Pageable;

public interface PageAware{

	public void setPageable(Pageable pageable);
	public Pageable getPageable();
	

}

