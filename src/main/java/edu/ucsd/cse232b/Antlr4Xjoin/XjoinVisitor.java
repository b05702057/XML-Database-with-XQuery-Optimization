// Generated from Xjoin.g4 by ANTLR 4.7.2

// The package keyword encapsulates a group of classes, sub packages and interfaces.
package edu.ucsd.cse232b.Antlr4Xjoin; // add the package name at the beginning of ANTLR generated Java files

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XjoinParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XjoinVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code FLWR}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFLWR(XjoinParser.FLWRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleSlashXQ}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleSlashXQ(XjoinParser.SingleSlashXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tagXQ}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagXQ(XjoinParser.TagXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apXQ}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApXQ(XjoinParser.ApXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code letXQ}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetXQ(XjoinParser.LetXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringXQ}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringXQ(XjoinParser.StringXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commaXQ}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommaXQ(XjoinParser.CommaXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varXQ}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarXQ(XjoinParser.VarXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceXQ}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceXQ(XjoinParser.BraceXQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleSlashXQ}
	 * labeled alternative in {@link XjoinParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleSlashXQ(XjoinParser.DoubleSlashXQContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#forClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForClause(XjoinParser.ForClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#letClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetClause(XjoinParser.LetClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(XjoinParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#returnClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnClause(XjoinParser.ReturnClauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parSatisfyCond}
	 * labeled alternative in {@link XjoinParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParSatisfyCond(XjoinParser.ParSatisfyCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceCond}
	 * labeled alternative in {@link XjoinParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceCond(XjoinParser.BraceCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orCond}
	 * labeled alternative in {@link XjoinParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrCond(XjoinParser.OrCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyCond}
	 * labeled alternative in {@link XjoinParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyCond(XjoinParser.EmptyCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andCond}
	 * labeled alternative in {@link XjoinParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndCond(XjoinParser.AndCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isCond}
	 * labeled alternative in {@link XjoinParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsCond(XjoinParser.IsCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqCond}
	 * labeled alternative in {@link XjoinParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqCond(XjoinParser.EqCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notCond}
	 * labeled alternative in {@link XjoinParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotCond(XjoinParser.NotCondContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleAP}
	 * labeled alternative in {@link XjoinParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleAP(XjoinParser.SingleAPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleAP}
	 * labeled alternative in {@link XjoinParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleAP(XjoinParser.DoubleAPContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#doc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoc(XjoinParser.DocContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceRP(XjoinParser.BraceRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleSlashRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleSlashRP(XjoinParser.DoubleSlashRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code textRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTextRP(XjoinParser.TextRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code attRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttRP(XjoinParser.AttRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parentRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParentRP(XjoinParser.ParentRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selfRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfRP(XjoinParser.SelfRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterRP(XjoinParser.FilterRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code commaRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommaRP(XjoinParser.CommaRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code childrenRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChildrenRP(XjoinParser.ChildrenRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tagRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagRP(XjoinParser.TagRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code singleSlashRP}
	 * labeled alternative in {@link XjoinParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleSlashRP(XjoinParser.SingleSlashRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqFilter}
	 * labeled alternative in {@link XjoinParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqFilter(XjoinParser.EqFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notFilter}
	 * labeled alternative in {@link XjoinParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotFilter(XjoinParser.NotFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andFilter}
	 * labeled alternative in {@link XjoinParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndFilter(XjoinParser.AndFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code isFilter}
	 * labeled alternative in {@link XjoinParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsFilter(XjoinParser.IsFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpFilter}
	 * labeled alternative in {@link XjoinParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpFilter(XjoinParser.RpFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringFilter}
	 * labeled alternative in {@link XjoinParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringFilter(XjoinParser.StringFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code braceFilter}
	 * labeled alternative in {@link XjoinParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceFilter(XjoinParser.BraceFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orFilter}
	 * labeled alternative in {@link XjoinParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrFilter(XjoinParser.OrFilterContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(XjoinParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#openTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpenTag(XjoinParser.OpenTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#closeTag}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCloseTag(XjoinParser.CloseTagContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XjoinParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#attName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttName(XjoinParser.AttNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XjoinParser#fileName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFileName(XjoinParser.FileNameContext ctx);
}