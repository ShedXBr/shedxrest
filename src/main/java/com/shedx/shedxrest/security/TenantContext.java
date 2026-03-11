package com.shedx.shedxrest.security;

public class TenantContext {
    private static final ThreadLocal<Long> currentCompany = new ThreadLocal<>();

    public static void setCompanyId(Long companyId){
        currentCompany.set(companyId);
    }
    public static Long getCompanyId(){
        return currentCompany.get();
    }
    public static void clear(){
        currentCompany.remove();
    }
}
