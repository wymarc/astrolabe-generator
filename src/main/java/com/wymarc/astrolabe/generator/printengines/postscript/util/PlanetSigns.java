/**
 * $Id: AstrolabeGenerator.java,v 3.1
 * <p/>
 * The Astrolabe Generator is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3 of
 * the License, or(at your option) any later version.
 * <p/>
 * The Astrolabe Generator is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * <p/>
 * Copyright (c) 2017 Timothy J. Mitchell
 */
package com.wymarc.astrolabe.generator.printengines.postscript.util;

import java.awt.geom.Point2D;

/**
 * This file contains tools for working with EPS
 * (Encapsulated PostScript)
 *
 * author    Timothy J. Mitchell <wymarc@gmail.com>
 *
 * Usage:
 * -- rotate the document to the desired angle
 * -- Call the planet as follows:
 *		var location:Point;
 *		location.X = 100;
 *		location.Y = 100;
 *		epsString += "\n" + printPlanetAt("Mars", location, .5, .5);		
 *
 */

public class PlanetSigns {

	private static String getSun(){
			
		String data = "";

		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 45 string readstring pop 0 42 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;&&2!.[;l:cI;:#5<'Yi(j#srWM";
		data += "\n" + ",-#N/8Li'%9.If\\HqkYu:Vs$=EWOJrYJrkX^aV%:E=5#slQ\\7I)aPXYU0M+qI)38Ep\\)KW]EDu'lnr";
		data += "\n" + "pPD1)t57JrWM^45i:'N*EEC6X&`ub\\/P>?\",0dJo]lC,`FlP]D-!S%<IJP`>4,H2Q.E9:Vd6Hl=1r4";
		data += "\n" + "h>.rVU2VYXbX%9h\\.bLgT2?/WCfPhB!Q7i[K^VATKC[:i'Sr)!92Or'Yd>5hQZ5T6N#?_/?ARG0OY.";
		data += "\n" + "H8pcrr8Pp_plI\\__:M>Wb/Mj/CsTpm/'`_f^s_euuQ#NM,`QdCo>kL[S7KN.kuIFsk$,0uVgHZu5W/";
		data += "\n" + "SNjWjlJR*&(X&-U^KWo!r[j$,l+_%M[du%GUC+HAc=;ZkX`-p>coicRb(ZU.PNr4KcF[E=O734ZrPQ";
		data += "\n" + "fP,/V\";PBbWZ0cC2s/I_h&rPe6Bdb.'NPPD7&+9~>";
		
		return data;
	}
	 
	private static String getMoon(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 48 string readstring pop 0 45 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3Vsg2o$I=k<Qi7mfW";
		data += "\n" + "Ispcj>EKCC-*r#Yuf5LJsJiVef>+33;*q%RP`\")#a69R0S9=U0#l+S&Sq5#F4h1XkhOr'CP#T0M4p2";
		data += "\n" + "uD_ArL?_6:C:&-pH3q(C/h-J%IZnrYM`4=rD(%5J^`rs._UiA?)\\q2[2L?WbuV=(Bs69>RaD):?*3j";
		data += "\n" + "G($5Yf]%ett#MmP9Cr+W*'g$RKq2)!+0A-UG#*hQI;QNnTr\\hN\\aPQML4<@8`VTN<(f1Mp<>lW/PZd";
		data += "\n" + "_ZSNtHN;>P>9O^9Y3Mh<X9lrYH=pZ/X?Cp`;o\"'?&M]XuMd9e_e*li;46Q6joj1ct)in546;tQK(lJ";
		data += "\n" + "H[^[<a*mU0Z?pfa[tCld<F,P5h%Rs+^^C7YgHDIhhQO5!.[5EG$7#Dg1_>TC\\%q=1I0[G<Ei/Q@E!o";
		data += "\n" + "S@TB_=*$nq)Q.^q7PgOQ@maAc@[Z]ToP)6.?U~>";
		
		return data;
	}
	 
	private static String getMercury(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 45 string readstring pop 0 42 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;&&2!.[;l:cK^9%/YW@%Ic=GIL,";
		data += "\n" + "+f^><ff]B1OXJN_nq8DFO7*Il,fEJP;!#lYp^'Cr,5%Tb9K^npWN1_nt,S3Pm%RFDi+4J5DW'H.]!N";
		data += "\n" + "<2>lku>.j%[g+_a=7ocV,iXTX&*=@l)c;=?`Hf`oueTV#4JlpB60A$k.O<;>MkH&NRe>QYLY2S\\21k";
		data += "\n" + "0\"glWnpT\"K6M0u2H0DK6F0,]hbe7d1:r\"BA<^uQuTm+\"<R4e,=22!acI3M\"Z\"0b@V*:9g5:k-_aVc6";
		data += "\n" + "H,ighJ_b/^F*=28hQ:opsnlB*N\\NkuNc.s$Ht?iMk/k55kT~>";
		
		return data;
	}
	 
	private static String getVenus(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 45 string readstring pop 0 42 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;&&2!.[;l:cIl-#5`DUhX;:#Sac";
		data += "\n" + "f+^CTf\\HqGgdM8.a6L_Cghs,TWSnb4\"!#7Q1$i.LeDr')d\"^ud=\"S@W,fR8,)lg#NIu4b(0/'KRNUN";
		data += "\n" + "CSOa-*Pr?p4Z&9<,,B)APT?uD:RrP]&b-:!g?\\7p\"=2Ba[!)`m8MTLQJFZ1g?5QLPNEO!W'=MZ#Mi;";
		data += "\n" + "<6bh%/0)NX(cXsoMX14[#2Pl(u7WHcKm=-KGHf_K<>\\.sY/RRY5n,3,O-(TNr.TW(R52V^?_)8hbDK";
		data += "\n" + "e[ZSnPSP4]P!^8(-TVgT+A]N@hb=q\"l[T+'H5&<k7nYcU*!-U>OT\"C=`ZNQEt_4YRg~>";
		
		return data;
	}
	 
	private static String getMars(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 45 string readstring pop 0 42 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;&&2!.[;l:cK^0k6+/g)\":\"B&`4";
		data += "\n" + "(L3$=:@r\"'qOIm;mFpZ)5gs*VT6E%)0T8H4l<h[fKEW`]Cgb?kMMPgT^4+2-\\H=;ZK!\"GoEX:C7gP+";
		data += "\n" + "%;kuTPO<GrWM+C5f`9E_>X*0IaT`DMVWHh:PVg5p,DqVa_-4VD&0`pD9lC_>ASIuPQ&O3\\Ai?OH`R.";
		data += "\n" + "r>e\\qVe%?)%Pm+5E,'[nq$?^+(_(YRbqgU&]b[iL(F5.ABK=0e2e6JLBirV<Xmnb=s''ZHJBIgYU50";
		data += "\n" + "=82JRY:a9[<B\\NSeW_n6FeY7j4_F)g/9j,E#jAA%34g+#esdYB/X8*RA(KV;,L&(gcA%Xkp>IC]#Y4";
		data += "\n" + "].9J(>PFW4^#/2ZqI;BZ9ujTg^m=8\"rf\"$Nl$i:S-i7*>-qiU-bWIOGr3*jbo>:\\`8#hZ$J,~>";
		
		return data;
	}
	 
	private static String getJupiter(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 45 string readstring pop 0 42 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;&&2!.[;l:cI<]#4k3th<m6ArYY";
		data += "\n" + "rq]](Ws4Am*DL:?-BphB^3reA0pEB&W;\"VR\"`jUuhaZ;V3`]I\"1;I!.;%S5*]qWN6Re\"-,Y,cm])-7";
		data += "\n" + "g\"+`ku>6*162KcdqV\\TI4BkJWD@,i$IrV!?^t25Q3qJ06Og'DB@@3IP2-SAf;qs+<PI>*%hJ*[]B-b";
		data += "\n" + "^9=j#\"=?CX)$!=aslP=8B.[>\"kea_&aeKT'cJ(n>tr75L.=bbOD\\_A)S6`8C;XB@(119mD%=7Fo@rf";
		data += "\n" + "_%ub\\cmRrQOS?He),:]K84d2#+`/GarY'6CG9Pm.!(#lZP3FJREeNX*a</=Pqa(1CSsj_k2I0~>";
		
		return data;
	}
	 
	private static String getSaturn(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 42 string readstring pop 0 39 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1\\!]?JLCh;-le!!%O6i(uAkrWrd[6\"'2l9HjECL;=";
		data += "\n" + "B3EVq^F%mPj.,P]bt3s(>]+E.&`qAfEq^LV8n)r='AOj%E9-\\Tc^2k3WNE9YmFs\"L'>V3!]Z%Gs+9:";
		data += "\n" + "h`fbr@V_OJPk?I$?Y,&<NK5:<N<^cRpN54U\".d0WbkXb=6q`=)Zl4=\\6NdWCR*In<4cIT<h%n,nOgR";
		data += "\n" + "OX]$/bmg\"9RiTIR0ediL'J`E_t3#^['HFdCeR#9`aiJ\"\\t?r-/(jrlangZ`X5]B;gNe'.Hp\\d^uG[]";
		data += "\n" + "O2C(5_sKh,W9JA:)H/Wb9/n;mlOl?P3SE~>";
		
		return data;
	}
	 
	private static String getUranus(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 45 string readstring pop 0 42 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;&&2!.[;l:cE]JrrRefh!+#AhAH";
		data += "\n" + "BM6XfHYN'\\luKL)8Fp`'2m\"(T.(D`DOp)Yf'%+]>6PSll#4bMI:6MtRANRm<U-GSklk5CkpF'F4:$L";
		data += "\n" + "I\\.)h=LIp-AMG*a(]K?gYRuiVcI0O3o\"lP;QSMe;j\\;_`FlP]D-\"8[[bYGu=6OiX*FdE%[(#C'B3cG";
		data += "\n" + "Q\"Ll1Fchnfm#DKtJlI:_LY8<&+j='ftrR3&X`'Q!d4Dnd(C<#E,1n!K\\]cF&hEr*CBgFZ+MChW#!:(";
		data += "\n" + "fXDSj+KaXkjG(^O`#e'qG45+(*7Q17HHFL5jH/D/<Ih5/0[X`b-tf>Am[Mgn`d^].6s?fcQ)W]i$Ah";
		data += "\n" + "g':+?i$&njHbYd*Va%f?-@I,7r%_(E_Y1,_>OJ,r%NGJW\\r3UC[(6W4p5Ydg~>";
		
		return data;
	}
	 
	private static String getNeptune(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 45 string readstring pop 0 42 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;&&2!.[;l:cJ7M#+o7`i'?f$IK^";
		data += "\n" + "\"25N37L9N!)KM+(o/Ggml9!b\\^3n+XU-#mPt'+B#-hSm<e3b2tJ3,h;n`Rn\\h4RH4'i5FTb<DgDR&N";
		data += "\n" + "CTd=Ui`&:2?c0n^M20NKg)D+DpMe9qRq'c?a*V3YPT?+$\"ebB+@YPT^&;TR>&H34/ZQp6WS?bt$C1&";
		data += "\n" + "X:4l.hQhk;(\"[p4:EEKd6#8rSpa6PE0ljEEOJn<j8XQF.Y3IEgEFdcT_6Nbfu-eOB4)c]8J^%/]3/.";
		data += "\n" + "XT;U1Ero^#T-83NbT+n(\\eqI0/G>q)j[!s-M$nm<?.<s%B7Iht?M]/C(g4r,_SE<X8s~>";
		
		return data;
	}
	 
	private static String getPluto(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 51 string readstring pop 0 48 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.Memf3=e!!!\"L5N#?sqE>2JJiq";
		data += "\n" + "t0GZ5\";q1Za0:H4R:k50_D@h:$Rr;Qh:8)R/;,Mk?YIPKc5q-:?5/=4PQ;[Ju9s\"p4]-Vu1DmJR0Oc";
		data += "\n" + "JS0G'c%$BJ$7diWRUp.\"/*rooP14'X]MSpVf:A/(6o8g:nP$cZgP5,ZrGt,X$sH'Ck(+ieV]:uSK,2";
		data += "\n" + "sDcZ-ll.:3-_!Y\"r%2uuJ\\70=M]\"TZGHcT`!p3JUJ,F?J9EmORi^C*->^p9%Q4^1.<e7s,*/M*4DGL";
		data += "\n" + "c%m2@;&0JQRrag\\9NX^ctrJj<\"jk*tM@ji*?%:fH[k:cfe]BoPK:\\D:.S5HB:Y)g9['=o^uC;(.2Lr";
		data += "\n" + "o%rdR$k7XZf>1=t4ccM]AZb(m0>A%$p.h9lYNuDnW=:lae$nfc<e,]~>";
		
		return data;
	}
	 
	private static String getHead(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 45 string readstring pop 0 42 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;&&2!.[;l:cK^9%/p@Eh_-r.c1q";
		data += "\n" + "H_^S#K=q>LNCMSP_Mnbd,^*fe\"qEG,2I8FM%\"(iRM'p#d9DT:YREMk4U2GKo8h+lhEeJUC!t0PVjBi";
		data += "\n" + "C0P.+0\\9./sB)05phj5g^YmMJkRWd2uQd`\"Hl`#<]Ws0-\"Nq)TC7\"N@Ku\\6gFMeT/NV:']LSp.b([E";
		data += "\n" + "jG*,\\+Q]%.6o6@HIi)f302N_XX?a:4;Q%p*@\\CWGqM^>CFQ@\\,TGfUqE3M3l\\[.fI/!Y(`PCT7/)??";
		data += "\n" + "6L^Z((<QMC*sq\"paC#WV@OXIg*T,TCb)\\mL-L>io#g/*t+-+d<2S&*5:<)\\\"n&DrLUakn$(#]4MD.L";
		data += "\n" + "e'#MGHu_0W/hq]AM/p74ID>5ue%NmHr7S*-9<.`d%qF=F]h9SDUqoZJrr9/p&cr~>";
		
		return data;
	}
	 
	private static String getTail(){
			
		String data = "";
		
		data += "\n" + "{/T currentfile/ASCII85Decode filter def";
		data += "\n" + "T 45 string readstring pop 0 42 getinterval";
		data += "\n" + "/F T/LZWDecode filter def";
		data += "\n" + "/m 0 def";
		data += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		data += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		data += "\n" + "0}if/m exch def}forall";
		data += "\n" + " F closefile T closefile}";
		data += "\n" + "%%BeginData:;";
		data += "\n" + "exec";
		data += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;&&2!.[;l:cK^9%)[H'i(M[kIKY";
		data += "\n" + "+H]A`1&PPk@IMSp^6a1OG3JV:h/n)O<Q,pe&IhsuSFZ2Y/\\_kn(\"1p0OuJN_o;2r!I!quOVIn7j3Xi";
		data += "\n" + "C0P?,cg\\cHBS;c^n5RLFZp1,Vpo<48%]T6\"+&C,'l,*YHN\\b\"?hN,h'\"%g[fAL_^<RqUt]3]DUlc&#";
		data += "\n" + ">FhZ9S'UkR/$Nn3$m8[77dKM_;hXQhfegB^4`F`tkC`Eg-F/!;[Pl2?[*IH8tY:n[+:&f7>l#g0HT%";
		data += "\n" + "42oeC4$Y4l4nNZ@_L[S*2Anq7Vp,%rbdk?3Qm:ic+^KrsKR*hWss$L>OerTVV'1>tp$RbMT,RI.2$N";
		data += "\n" + "LUYie@Hnnl-]<HWZb\\?%^K_0CpE4;<m'So.U52#jNDhC&^'GtgU8q(+%/D@APB?r7cU+l-_^G@1Wur";
		data += "\n" + "M~>";
		
		return data;
	}

    /**
     * retrieves the requested planet glyph
     *
     */
    public static String getPlanet(String planet){
        String data = "";

        if (planet.equals("Sun")){
            data = getSun();
        }else if (planet.equals("Moon")){
            data = getMoon();
        }else if (planet.equals("Mercury")){
            data = getMercury();
        }else if (planet.equals("Venus")){
            data = getVenus();
        }else if (planet.equals("Mars")){
            data = getMars();
        }else if (planet.equals("Jupiter")){
            data = getJupiter();
        }else if (planet.equals("Saturn")){
            data = getSaturn();
        }else if (planet.equals("Uranus")){
            data = getUranus();
        }else if (planet.equals("Neptune")){
            data = getNeptune();
        }else if (planet.equals("Pluto")){
            data = getPluto();
        }else if (planet.equals("Head")){
            data = getHead();
        }else if (planet.equals("Tail")){
            data = getTail();
        }

        String planetString = "";
        planetString += "\n" + "save 9 dict begin";

        planetString += "\n" + data;

        planetString += "\n" + "%%EndData";
        planetString += "\n" + "end restore";

        return planetString;

    }

    /**
     * Draws the requested planet glyph centered on given location at given scale
     * planet glyphs are 36 wide by 28 high
     *
     */
    public static String placePlanetAt(String planetIn, Point2D.Double location, Double scaleX, Double scaleY){

        String planetData = getPlanet(planetIn);
        String planet = "";

        planet += "\n" + "gsave";
        planet += "\n" + "newpath";
        planet += "\n" + (location.x - (18.0*scaleX)) + " " + (location.y - (14.0*scaleY)) + " translate";
        planet += "\n" + scaleX + " " + scaleY + " scale";
        planet += planetData;
        planet += "\n" + "grestore";

        return planet;
    }
	
}