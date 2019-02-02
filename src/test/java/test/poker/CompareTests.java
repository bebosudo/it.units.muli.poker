package test.poker;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import poker.kata.*;

public class CompareTests {

//    @Test
//    public void comparePairs(){
//        Hand h1 = new Hand("Kc 9s Ks Ad 4d 3d 2s");
//        Hand h2 = new Hand("Kc 9s Ks Qd 4d 3d 2s");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//
//        h1 = new Hand("Kc 9s Ks Qd 4d 3d 2s");
//        h2 = new Hand("Kc 9s Ks Ad 4d 3d 2s");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        h1 = new Hand("Kc 9s Ks Ad 4d 3d 2s");
//        h2 = new Hand("Kc 9s Ks As 4d 3d 2s");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("Tc 9s Ks Td 4d 3d 2s");
//        h2 = new Hand("Tc 9s Qs Ts 4d 3d 2s");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//
//        h1 = new Hand("Tc 9s Ks Td 4d 3d 2s");
//        h2 = new Hand("Tc 9s Qs Ts 4d 3d 2s");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//    }

//    @Test
//    public void compareDouble(){
//        // KK 99
//        Hand h1 = new Hand("Kc 9s Ks 9d 5s 5d 6d");
//        Hand h2 = new Hand("Kc 9s Ks 9d 5s 5d 6d");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("Kc 9s Ks 9d As 5d 6d");
//        h2 = new Hand("Kc 9s Ks 9d 5s 5d 6d");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//
//        h1 = new Hand("Kc 9s Ks 9d Qs 5d 6d");
//        h2 = new Hand("Kc 9s Ks 9d As 5d 6d");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        // 55 33
//        h1 = new Hand("3d 4h 5h 3s Ad 5s 6h");
//        h2 = new Hand("3d 4h 5h 3s Ad 5s 6h");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("3d 4h 5h 3s Ad 5s 6h");
//        h2 = new Hand("3d 4h 5h 3s Kd 5s 6h");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//
//        h1 = new Hand("3d 4h 5h 3s 7d 5s 6h");
//        h2 = new Hand("3d 4h 5h 3s 9d 5s 6h");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        // TT 99
//        h1 = new Hand("Th 9d Td 8c 9h 3d Js");
//        h2 = new Hand("Th 9d Td 8c 9h 3d Js");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("Th 9d Td Ac 9h 3d Js");
//        h2 = new Hand("Th 9d Td 8c 9h 3d Js");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//
//        h1 = new Hand("Th 9d Td 8c 9h 3d Js");
//        h2 = new Hand("Th 9d Td Jc 9h 3d Js");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//    }
//
//    @Test
//    public void compareSet(){
//        // 444
//        Hand h1 = new Hand("3d 4h 4s Ad Kd 9c 4c");
//        Hand h2 = new Hand("3d 4h 4s Ad Kd 9c 4c");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("3d 4h 4s 2d Kd 9c 4c");
//        h2 = new Hand("3d 4h 4s Ad Kd 9c 4c");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        h1 = new Hand("3d 4h 4s 2d Kd 9c 4c");
//        h2 = new Hand("3d 4h 4s Qd Jd 9c 4c");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//
//
//        // QQQ
//        h1 = new Hand("Qh Ks Ad Qs 9c Qc 4c");
//        h2 = new Hand("Qh Ks 3d Qs 9c Qc 4c");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//
//        h1 = new Hand("Qh 3s 2d Qs 9c Qc 4c");
//        h2 = new Hand("Qh Ks 3d Qs 9c Qc 4c");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        h1 = new Hand("Qh Kd Ad Qs 9c Qc 4c");
//        h2 = new Hand("Qh Ks 3d Qs 9c Qc 4c");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//    }
//
//
//
//    @Test
//    public void compareStraight(){
//        Hand h1 = new Hand("As 2d 3s 4h 5c Kd Qs");
//        Hand h2 = new Hand("6s 2d 3s 4h 5c Kd Qs");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        h1 = new Hand("As 2d 3s 4h 5c Kd Qs");
//        h2 = new Hand("Ad 2d 3s 4h 5c Ks Qs");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("As 2d 3s 4h 5c Kd Qs");
//        h2 = new Hand("3s Kd 4h As 5c Qs 2d");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("7d 8c 9c Ts Jd 3c Ac");
//        h2 = new Hand("3s Kd 4h As 5c Qs 2d");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//
//        h1 = new Hand("7d 8c 9c Ts Jd 3c Ac");
//        h2 = new Hand("Kd Ac 6c Td Jd 2c Qc");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        h1 = new Hand("7d 8c 9c Ts Jd 3c Ac");
//        h2 = new Hand("7d Ac 9c Ts Jd Qc 8c");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        h1 = new Hand("Kd Ac Ad Td Jd Ah Qc");
//        h2 = new Hand("Kc Ac Ah Td Jh Ah Qc");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("Kd Ac Ad Td Jd Ah Qc");
//        h2 = new Hand("Kc 3c 9h Td Jh 2h Qc");
//        assertThat(h1.compareTo(h2), is(equalTo(1)));
//    }
//
//
//    @Test
//    public void compareFlush(){
//        Hand h1 = new Hand("Ks As 2s Ts Js 3c Qh");
//        Hand h2 = new Hand("Ks As 2s Ts Js 3c Qh");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("Ks As 2s Ts Js 3c Qh");
//        h2 = new Hand("Ks 3s 2s Ts Js 3c Qh");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        h1 = new Hand("Ks 4s 2s Ts Js 3c Qh");
//        h2 = new Hand("Ks 3s 2s Ts Js 3c Qh");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//
//        h1 = new Hand("Ks As 2s Ts Js 3c Qh");
//        h2 = new Hand("Kh Ah 2h Th Jh 3c Qc");
//        assertThat(h1.compareTo(h2), is(equalTo(0)));
//
//        h1 = new Hand("Ks 3c 3s 2s Qh Ts Js");
//        h2 = new Hand("Ks 3c As 2s Qh Ts Js");
//        assertThat(h1.compareTo(h2), is(equalTo(-1)));
//    }
    
}
