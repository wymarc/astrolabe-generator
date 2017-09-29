/**
 * $Id: AstrolabeGenerator.java,v 3.0
 * <p/>
 * The Astrolabe Generator is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2 of
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
 * Copyright (c) 2014, 2015 Timothy J. Mitchell
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
 * -- Call the sign as follows:
 *		var location:Point;
 *		location.X = 100;
 *		location.Y = 100;
 *		epsString += "\n" + printSignAt("Aries", location, .5, .5);		
 *
 */

public class ZodiacSigns {

	private static String getAries(){
			
		String sign = "";
		
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3V*Cp3Zc/a+\"TVms!";
		sign += "\n" + "OZrr.,V?Z5_7na?EA_qacQH+l9<Nusq]n9etAr-u@iP,HNe>837@-8Pr*c:Km`9W'[<eFn/PS&aFT#";
		sign += "\n" + "P3BC^Q6';O*B(L./SqC,&t]K$9:S:^T+\\VFE4-WYZP^B4!g3;<f>G#dJI4<[huI)/Tj.7f'R[o>@I!";
		sign += "\n" + "Sli'C_YMLrlN$]>M+,*!sn(P\\Z)'$]Unl\\)O$2YsVjKZ2K*cj?,ctI;P.l9TK*5C^F=\\,*3r9LTJEY";
		sign += "\n" + "j)\"R_s6tmWU[\\'d>*!J*o7#4V\"A9ZJonFHjKX_#Bd!:m$u@&<lGKmaP1_5p4AR\\iRBLm4T=&q*9Ln7";
		sign += "\n" + "5/0UoJ*r*i4^R$D^[(FefcPM;A)E6L40':ln3DoSW3AO&3\\JLXC>)/B5ck(h~>";
		return sign;
	}

	private static String getTaurus(){
			
		String sign = "";
	
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3Vsg3$]7K\"b\"3/LU\\";
		sign += "\n" + "HJ#igM#+E)J_rWM\\>&*!'Qe,>m<&&=\\'o_aS<!_:#e19\"f1:G.gFf^eburI4lo_&k?*Zi0\\'NksXL$";
		sign += "\n" + "5F<^pS5KrO4lH4RffKaB=.N2+pdY,5cG;^oFaYoTi^1FL`J5V\\\"_NK2@C67?N7<0m$50c*nLIlh.a>";
		sign += "\n" + "8*DVN<Vqp8(BDo:36CZ^P=@`>m&!*_Sj7%G>6Ec]T@B:%L`#fYY?KZ-N.g>fAVl/Q3RJZC:Dg^&NG$";
		sign += "\n" + "@#0dqQ/5;WtVB2%+7*b3CZ`#O\\X2a5h(3gO'_cVnn!Kh=K@?ZAl*Ld%B\"j[8U3e[dJK!?L8\\Y<5*YP";
		sign += "\n" + "(LJ^0Q]%a9\\K9J&W`NW,n+--XL'nFaX:NaX`[#<qq#M[U40[P>X<WJR[-pqVo:R3~>";
		
		return sign;
	}
	 
	private static String getGemini(){
			
		String sign = "";
	
		sign += "\n" + "T 42 string readstring pop 0 39 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWm&]2mdBKC!!%O6i(u<Npcegi_4_F<r$Dp`L-q";
		sign += "\n" + "YRpln)o&WB0=;)mW?3u*iHA+7Pj4U2CH]HD7*>OGWIQqV*j3Mb5C2k3S!Y5,*sF$H<oBN.#=(6(h[;";
		sign += "\n" + "J#=MOoPImR`h@H-l#VZ<ier0<BAr:]2u2eCR.$SP/1b&:oBPYA[^Cs5Kp8c\\n/VCGJ$Ud2k/q&@90&";
		sign += "\n" + "XhtS`JY6tHM^p,j]2E)5.`FL]jr2)t707O#bDgV2%4Y*qUZ]e%bInb+7bb*T`%.4A'JSZG*/Z-!oYM";
		sign += "\n" + "8Df4$DHPEPL=K2k29;Y%`F8no#M(#Q~>";
	
		return sign;
	}
	 
	private static String getCancer(){
			
		String sign = "";
	
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3VrXp_3Vq!\"Zl8!!#";
		sign += "\n" + "+f%f\\i1O72fNrVHYD6!s*%S-h7[O2n$#\\EhuGs*k(1chGWEpA'3NhiGQ7q8WDb5Fh>%7.,+[PW7U-C";
		sign += "\n" + ")g7]n:<a\\_tX'Tp](:m+5d,.*$6)M;C`Kngg57gUrK$Lb9`GR'=#hioW&^qh/@es?r$LId^`bp>:XR";
		sign += "\n" + "egi\\*cY$S/R/'JdjJAbo5cMSr&s'`!4A9b1m_X.DQgnO=S/fu<?]O2fTbZ/WRE6i66NdImL2i*.Mi(";
		sign += "\n" + "r/TP@&7Tg9k!Fh8alWf#G.g#aLTP)VX$:)B7di43-7?l!'_1'$JCuEb'S.VRquAeTYes4a/8l)Ll>r";
		sign += "\n" + "&76a@c\\Udf;>*\\b(&RCl@Q+$`J%sRT]kAqsJ&#smcNC!OZd.OF/aNi'26X(IhRuQ4n?+Rflm0&+M9<";
		sign += "\n" + "imY\"sqZs\"Qmr0a=l)iSO4:#olLA61+kbXeG.C+MkP7R^Tu79^t=]2+P<4Y,o\\'Upqr#X9dh!:tu+6[";
		sign += "\n" + "W*8'^dLgOCFZI4+mQPZWPda'U<^Z*l:4J,*t+_a~>";
	
		return sign;
	}
	 
	private static String getLeo(){
			
		String sign = "";
	
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3VrSp_3Vh\"b4)?crN";
		sign += "\n" + "7)#lfX?i)(>\\qBZ]27:Pbb]8cUsO$h^ka=EhQ-$[7/n9>Up`r^W1f^OWC+WeCMUVZG!r*B;oHd66MM";
		sign += "\n" + "4>=>&#2AJE/?[lqZs-\"C/m<k<VHcqefF5Ug^8%#T[dH[HNf;O<3+^><?\"Z$gJMn=T@MYlILC.p478E";
		sign += "\n" + "GNS:(NJ(*iT46@='F1u%`0O+5h$?*=9mr^SS^=S@]g[Gu[70aE;`RQCP%38I;Hd177GjT(G;cVH8C0";
		sign += "\n" + "Ff<Y@Y2@@.Oq8V2K2'hX2jh]a\\V.a+R[^)B3;sGK?dlTD.]kiifW>>Ak&-#BPsMk$#D\\%>U+rmbqG2";
		sign += "\n" + "hJ.bZ`rB?A3;q%oO0S8qrV@TWO6bl33%^TEI_\"a=f!IFiKAO=&:7;<K/T<bK!3-:@$O*0lC7P;Ybkl";
		sign += "\n" + "AD3=OYk^VB'5Ih48q3o]nO8q]=R.]]@!Wh>.R~>";
		
		return sign;
	}
	 
	private static String getVirgo(){
			
		String sign = "";
		
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3Vr;kPPV`57bQ'#l%";
		sign += "\n" + "5F)#f6,@IT&B2>7Q-`Zn=\\r+7r1KLd>mBP^^/pP-g8MYJ_;^FarYjfA31eGh+P94,#,qn`(^I]qm4a";
		sign += "\n" + "s@f22hJfBFD>AFM)h<Y+KkPu;OmcU<U\"R-h+FkPX$L;3q^%P4(UHmg0Db=ld:p*qDGuCmf&pYMg`L1";
		sign += "\n" + "8m)#H@[+=.0/1'/D.YLN31?paZCO_pbmk+EC)P)u5AodWiC]>oRZXu$Nb4Q7pE44/6Du5!@Y5Ztq-i";
		sign += "\n" + "+0eHU\"14fe)YT'Z+3ehr?#gq3IM0!o\\034Gf*EQb_L#G]f$G(LshHDL3#OGAk/_[Gpqf8%bTk\"f:L\"";
		sign += "\n" + "<,HcEgK_(1mT%!NnbhS@96U:1s,OhiWp`jFl_oU(T`Zdk5k`$dcqGAM61A=<PX*92+U^V5U`0b/d-M";
		sign += "\n" + "nd6''bWpUfVT\"TC\"b6a6V<YEVl,0C?(/UFXXT@)k^Yq>p~>";
		
		return sign;
	}
	 
	private static String getLibra(){
			
		String sign = "";
	
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3VrWp_3VgqguB9OB-";
		sign += "\n" + "`'rrQ!2a7(5Kr[Ro(`StS*r*fUFO%:L8fc5`<+-XWP;.tWbrt1%$g[F>>p`DDSJ_u\"Q/G=KVK\"lg9$";
		sign += "\n" + "0;MN5'T>i0IOLJ^HI07:\\.0^<ioH)<bZXLI/3YBYZPZ5q\\>FAB=(iZ<!n&)qbaEKD0J%K_>c6ecX@V";
		sign += "\n" + "_\"8]V2Z9`DOb/O@^JUcZ6('ApkjZohIVms>J)L74Jf&t]^^2!I.aP--/q],>>@aO?qq9;q1o<$YZ0%";
		sign += "\n" + "WdndmpU_<c\"&7`qL-3R-Q65H3X3C\"+3lqqs)K?kJFT=[PRm%[mqIeD/F^$Y\",<jIePh1Hlf0.cDpPR";
		sign += "\n" + "5-Ed=h8?J_.t9Wm(9n@6=?W,mQ.s%IZ-?4d!&O*_HMss<CUh,/b%qXk+6Wt~>";
		
		return sign;
	}
	 
	private static String getScorpio(){
			
		String sign = "";
	
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3VoZp\\Xd'nU*VG&&\\";
		sign += "\n" + ";D(Cc/;T+K).;Bm!7`$7!=e64dJO%<cd1&MY$+-Rf-YZP]Rr@<In+6iPG&]$>V4<3haSCRQtTMT<$3";
		sign += "\n" + "[GQ$8#/Es[$U[`M*IM.hsL7R^6r'(<bk^Hj_go#YZP^@(&\\cE33pq9DV;))#@k8KBX!C8>,+?\"e*^;";
		sign += "\n" + "Ljn=I)ZsE.Q4,'PaJ>?VP:YS#>TnLKfn[BtEm=([?c!KkMNL#$CcTh%5>o&2cT@7OsRXHfl<N+McFr";
		sign += "\n" + "Laj.YCX@eoA\"Ip\"L_+aDP\\GD?gr=hp9(3e/>)%+%4%E31Ol_Nk[is4jVV@mVWDMc?FY\"%f^mpjmi/Z";
		sign += "\n" + "SptCVh<Oc!hqfo6b&Pb#DL-C@O&u7G.SNdPk::@(m//p5r\"O`=MF4+h0M0+_~>";
	
		return sign;
	}
	 
	private static String getSagittarius(){
			
		String sign = "";
	
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3Vsg3\"t-Sc[n7:OLS";
		sign += "\n" + "g1r<.Sc+)jb-%M](a_&)Wmr!i\"BJ\\E7fQm9HRkC^hu8GF<V*?B\\q,u=;`2A[^073I_\\bS\"POTMB-\\p";
		sign += "\n" + "q6lXqjGE:e@K3LSPOseB=0-mef.Yg6YH(\\$uQ;(RRA>uW3d/?)P9Bu:c_PSgJX`[ld-mSWj*/C=!op";
		sign += "\n" + "dA0U\\r^gM+`4=1jR5cdh_Q:sD:$!Y8OD0U?O0+X5NjYL$*eCsG`Vr'+PBDus<Vpo67'HK(J'7[IGYa";
		sign += "\n" + "ZY<%Y&l[hY0'Y`nP]'U$l^LrV[[LA`$nfQ(7i?$`_3BGa!!b(Tg>\\J,~>";
		
		return sign;
	}
	 
	private static String getCapricorn(){
			
		String sign = "";
	
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3Vsg2Z+$`O+4uQjqm";
		sign += "\n" + "\"Trr.9F+MemNc4&$O#N0OZr!jYJHpO>VGT22'\"*)@(82pH\\r^VI9i(SAXc>*BJ673Flbh?V-EQhffB";
		sign += "\n" + "^>Wfrf*N-cmON!rkG@/l4<X%)Nb5J_5am]j[QFZYX>Jb$R?4g\",B+s[:fH8b?V1(mT2J%VA3CC_4S2";
		sign += "\n" + "-1[b&@>2S7<2?!@\\DS4GuE%F`>pPXCQTC'-H0#5*8AncH*rU/j=Ze4kQ3$$%&I*6*(GC#:?rtrI\\Wf";
		sign += "\n" + "SfUG=cAtCGjfrri9tHIqprHc,If^^9V`;p\"g33dE^rj/[Xk%cXh%VA[+)MIEPd@J+iX4b2-XE@!1(0";
		sign += "\n" + "=-qfGPF:adIP<Es$T[jnGbBGBT@UO+[a'MiUc[j`!8G<SMu:+G:HQ4e_9.](dOs`7'\">_sMVe:e9Cg";
		sign += "\n" + "O*5L5e8Y(Z3>pnardFAE2dldOqJZE?#\"Xrg1j=U_EkqkEAmnBq5R/;nQXJ:I~>";
		
		return sign;
	}
	 
	private static String getAquarius(){
			
		String sign = "";
	
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3Vsg-d2J+!dHrb&1?";
		sign += "\n" + "H8\\H77&iNN\"FrZ_K&&)]bJr,M_.5KAoTq&B:&s(B-7n+g3,>l/NNhJ`.I\\btZ@Zsf$i`%_2'Ld[<o6";
		sign += "\n" + "2C@6s,BmndF](\\56?thALpps%KBIY\\_lk#*$W!/YLm3*(`N@&A[5KTp3Te^gB2B0X-VB9%d@B5^MC#";
		sign += "\n" + "WUTW'U]>1Sqqi1mdGIbXG2>?_Os(o[1h9Y\\A7g\"UOiUY[6Q7hiFGNUe`.h7%0UU\"DQ=nhV*ad(60+B";
		sign += "\n" + "k])G2mSV5k2B*3r_&KDt!0rX#f]$T@@n!qL%H^3NhIUpM_Z9A!)?,fQ1>g^2inHjEEXJ%5)hYZ,#\\5";
		sign += "\n" + "=M#Y^\\Gg^DX8YkTed(cOHoG4N?B9caVJP;hr1IiSZJ;MK];#3Y&#8L9[7nL[o0R=>,6,Lf8\\2:3a5U";
		sign += "\n" + "\".T<!o)\\i`Ucr5HLmqp;o1&&]s:7I48L(osn\\i:8af>BZ^#lItU@]kg?7GJpNif$g,3$QAt4Cih58X";
		sign += "\n" + ",>rJ)9.I0'Y6^coH*A3#S`a'U6>EZ/$(BiI)[ijo\"+1ilYB9-aN\\dg1-oRL!W~>";
		
		return sign;
	}
	 
	private static String getPisces(){
			
		String sign = "";
	
		sign += "\n" + "T 48 string readstring pop 0 45 getinterval";
		sign += "\n" + "/F T/LZWDecode filter def";
		sign += "\n" + "/m 0 def";
		sign += "\n" + "{255 div m 1 add dup 3 eq{pop setrgbcolor";
		sign += "\n" + "36 28 false[1 0 0 -1 0 28]F imagemask";
		sign += "\n" + "0}if/m exch def}forall";
		sign += "\n" + " F closefile T closefile}";
		sign += "\n" + "%%BeginData:;";
		sign += "\n" + "exec";
		sign += "\n" + "!!!!2&J632+u;#26psFC<E3XCAp8HCLkpkTR@1(TWk5mTbfn;eh;.MemJm4eJ3Vsg3$\\C9s'NR4n+$";
		sign += "\n" + ";^cMp,ES8[VLrT=$8]OML$e.(F4J\\36>.27M+s)7r^Ws?Q`s!<[sj72<ndK^SX`?7s$KIV<NKY_>\")";
		sign += "\n" + "ANI36@Vk2oJbSYSOJ)qiH0s\"2O>/1]AM+aS.]+'Ti^1&)[2PP6Cq?I<k=!FfhS^)lLug*esC*EfWN]";
		sign += "\n" + "829)1kXAb>WG\\oXR:;G`V=M*C4`I>4(?r,Qi<h0,]jWKh(XsWh-J\\1MSW`>XeT[[I-Q!I+f=)c6rhY";
		sign += "\n" + "8(U%IS.dD(g3@bn#*%^XKA+#Q>esT?tsE3=&%Pa_p*VXSb0IRD6c+FF-.kYA2><[Iu3McQ!=8D\";eC";
		sign += "\n" + "]DY5tmHhPjnt-AF4qm@lB]*%>'GJpX30+_ZJj2dNL<(:u&cr~>";
	
		return sign;
	}

    /**
     * returns the requested glyph by name
     *
     */
    public static String getSign(String sign){
        String signData = "";

        if (sign.equals("Aries")){
            signData = getAries();
        }else if (sign.equals("Taurus")){
            signData = getTaurus();
        }else if (sign.equals("Gemini")){
            signData = getGemini();
        }else if (sign.equals("Cancer")){
            signData = getCancer();
        }else if (sign.equals("Leo")){
            signData = getLeo();
        }else if (sign.equals("Virgo")){
            signData = getVirgo();
        }else if (sign.equals("Libra")){
            signData = getLibra();
        }else if (sign.equals("Scorpio")){
            signData = getScorpio();
        }else if (sign.equals("Sagittarius")){
            signData = getSagittarius();
        }else if (sign.equals("Capricorn")){
            signData = getCapricorn();
        }else if (sign.equals("Aquarius")){
            signData = getAquarius();
        }else if (sign.equals("Pisces")){
            signData = getPisces();
        }

        String signString = "";
        signString += "\n" + "save 9 dict begin";
        signString += "\n" + "{/T currentfile/ASCII85Decode filter def";

        signString += signData;

        signString += "\n" + "%%EndData";
        signString += "\n" + "end restore";

        return signString;

    }

    /**
     * returns the requested glyph by number
     *
     */
    public static String getSignNum(int sign){
        String signData = "";

        switch (sign)
        {
            case 1:
                signData = getSign("Aries");
                break;
            case 2:
                signData = getSign("Taurus");
                break;
            case 3:
                signData = getSign("Gemini");
                break;
            case 4:
                signData = getSign("Cancer");
                break;
            case 5:
                signData = getSign("Leo");
                break;
            case 6:
                signData = getSign("Virgo");
                break;
            case 7:
                signData = getSign("Libra");
                break;
            case 8:
                signData = getSign("Scorpio");
                break;
            case 9:
                signData = getSign("Sagittarius");
                break;
            case 10:
                signData = getSign("Capricorn");
                break;
            case 11:
                signData = getSign("Aquarius");
                break;
            case 12:
                signData = getSign("Pisces");
                break;
        }

        return signData;

    }

    /**
     * Retrieves the requested glyph by number and passes the data to the print function
     *
     */
    public static String placeSignNumAt(int sign, Double x, Double y, Double scaleX, Double scaleY){

        Point2D.Double loc = new Point2D.Double(x,y);
        return placeSignNumAt(sign, loc, scaleX, scaleY);
    }

    /**
     * Retrieves the requested glyph by number and passes the data to the print function
     *
     */
    public static String placeSignNumAt(int sign, Point2D.Double location, Double scaleX, Double scaleY){

        String signData = getSignNum(sign);
        return place(signData, location, scaleX, scaleY);
    }

    /**
     * Retrieves the requested glyph by name and passes the data to the print function
     *
     */
    public static String placeSignAt(String sign, Point2D.Double location, Double scaleX, Double scaleY){

        String signData = getSign(sign);
        return place(signData, location, scaleX, scaleY);
    }

    /**
     * Draws the requested sign centered on given location at given scale
     * Zodiac glyphs are 36 wide by 28 high
     *
     */
    private static String place(String signData, Point2D.Double location, Double scaleX, Double scaleY){

        String sign = "";

        sign += "\n" + "gsave";
        sign += "\n" + "newpath";
        sign += "\n" + (location.x - (18.0*scaleX)) + " " + (location.y - (14.0*scaleY)) + " translate";
        sign += "\n" + scaleX + " " + scaleY + " scale";
        sign += signData;
        sign += "\n" + "grestore";

        return sign;
    }
	
}