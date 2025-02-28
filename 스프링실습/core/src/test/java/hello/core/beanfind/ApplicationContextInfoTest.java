package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName(" 애플리케이션 모든 빈 출력하기 ")
    void findAllBean(){
        String[] banDefinitionNames=ac.getBeanDefinitionNames();
        for(String beanDefinitionName:banDefinitionNames){
            BeanDefinition beanDefinition=ac.getBeanDefinition(beanDefinitionName);

            // 스프링 자체에 있는 빈은 제외하고 출력
            if ( beanDefinition.getRole()== BeanDefinition.ROLE_APPLICATION){
                Object bean=ac.getBean(beanDefinitionName);
                System.out.println("name:"+beanDefinitionName+"object"+bean);
            }

        }
    }
}

