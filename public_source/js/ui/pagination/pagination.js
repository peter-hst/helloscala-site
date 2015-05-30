/** @jsx React.DOM */
var React = require('react');
var __range = require('lodash/utility/range');
var __isNaN = require('lodash/lang/isNaN');
var __forEach = require('lodash/collection/forEach');

var logger = require('../util/logger');

var DEFAULT_LIMIT = 10;

var Pagination = React.createClass({
    propTypes: {
        curPage: React.PropTypes.number.isRequired,
        totalPage: React.PropTypes.number.isRequired,
        onPageTo: React.PropTypes.func.isRequired,
        limit: React.PropTypes.number,
        totalItems: React.PropTypes.number
    },
    getDefaultProps: function () {
        return {
            className: 'pull-right',
            paginationClassName: 'pagination pagination-sm',
            activeClassName: 'active',
            curPage: 1,
            totalPage: 0,
            limit: DEFAULT_LIMIT,
            onPageTo: function (pageTo) {
                logger.warn('No definition of "onPageTo" function, the current pageTo is received:' + pageTo);
            }
        };
    },
    onClick: function (evt) {
        evt.preventDefault();
        var pageTo = parseInt(React.findDOMNode(evt.target).getAttribute('data-page-to'));
        this.props.onPageTo(pageTo);
    },
    createPageRange: function (props) {
        var totalPage = props.totalPage;
        if (props.totalItems && props.limit) {
            totalPage = parseInt((props.totalItems + props.limit - 1) / props.limit)
        }

        if (!totalPage) {
            return [];
        }
        //console.log('createPageRange: ' + JSON.stringify(props));
        if (totalPage === 1) {
            return [1];
        }

        var curPage = props.curPage || 1;
        var limit = props.limit || DEFAULT_LIMIT;
        var mod = curPage % limit;
        //console.info(curPage + ' - ' + limit + ' - ' + mod);
        if (__isNaN(mod)) {
            return [];
        }

        var begin = curPage - (mod || limit) + 1;
        if (begin < 1) {
            begin = 1;
        }
        var end = begin + this.props.limit;
        if (end > totalPage) {
            end = totalPage;
        }
        //console.info(begin + ' - ' + end);
        return __range(begin, end + 1);
    },
    createLinks: function () {
        var pageRange = this.createPageRange(this.props);
        logger.info('pageRange: ' + JSON.stringify(pageRange));
        var idx = 0;
        var links = [];
        if (pageRange[0] > this.props.limit) {
            links.push(
                <li key={'p'+idx}>
                    <a href="javascript:;" aria-label="上一页" data-page-to={pageRange[0] - 1} onClick={this.onClick}>
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            );
            idx += 1;
        }

        __forEach(pageRange, function (pageNo) {
            if (this.props.curPage == pageNo) {
                links.push(
                    <li className={this.props.activeClassName} key={'p'+idx}><a href="javascript:;">{pageNo}</a></li>
                );
            } else {
                links.push(
                    <li key={'p'+idx}>
                        <a href="javascript:;" aria-label={'第' + pageNo + '页'} data-page-to={pageNo}
                           onClick={this.onClick}>{pageNo}</a>
                    </li>);
            }
            idx += 1;
        }.bind(this));

        if (pageRange[pageRange.length - 1] < this.props.totalPage) {
            links.push(
                <li key={'p'+idx}>
                    <a href="javascript:;" aria-label="下一页" data-page-to={pageRange[pageRange.length - 1] + 1}
                       onClick={this.onClick}>
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            );
        }

        return links;
    },
    render: function () {
        var navProps = {className: this.props.className};
        if (this.props.style) {
            navProps.style = this.props.style;
        }
        return (
            <nav {...navProps}>
                <ul className={this.props.paginationClassName}>
                    {this.createLinks()}
                </ul>
            </nav>
        );
    }
});

module.exports = Pagination;
