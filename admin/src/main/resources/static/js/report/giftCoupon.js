$(function () {
    var months = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        margin = {
            left: 30,
            right: 60,
            bottom: 30,
            top: 20
        },
        width = $('div.container').width()-50 - margin.left - margin.right,
        height = 450 - margin.bottom - margin.top,
        xScale = d3.scale.ordinal().rangeRoundBands([0, width]).domain(months),
        yScale = d3.scale.linear().range([height, 0]),
        url = global.context + '/admin/parkingCouponReport/data';

    var r = 5,
        font_size = '24px',
        use_color = 'steelblue',
        exchange_color = '#e6550d';

    d3.json(url, function (error, data) {
        if (error)
            console.log(error);
        draw(data);
    });

    d3.select('#year').on('change', function () {
        var year = d3.select(this)[0][0].value;

        d3.json(url + '?year=' + year, function (err, data) {
            if (err)
                throw err;
            reDraw(data);
        });
    });

    function reDraw(data) {
        var useExtent = d3.extent(data, function (d) {
            return d.useNumber;
        });

        var exchangeExtent = d3.extent(data, function (d) {
            return d.exchangeNumber;
        });

        yScale.domain([0, Math.max(useExtent[1], exchangeExtent[1])]);

        var xAxis = d3.svg.axis().scale(xScale).orient('bottom');
        var yAxis = d3.svg.axis().scale(yScale).orient('left');

        d3.select('#xAxis').call(xAxis);
        d3.select('#yAxis').call(yAxis);

        var useLine = d3.svg.line()
            .x(function (d, i) {
                return xScale(months[d.date - 1]) + xScale.rangeBand() / 2;
            })
            .y(function (d) {
                return yScale(d.useNumber);
            });

        var exchangeLine = d3.svg.line()
            .x(function (d, i) {
                return xScale(months[d.date - 1]) + xScale.rangeBand() / 2;
            })
            .y(function (d) {
                return yScale(d.exchangeNumber);
            });

        d3.select('#line-use')
            .datum(data)
            .attr("class", "line-use")
            .attr("d", useLine);

        d3.select('#line-exchange')
            .datum(data)
            .attr("class", "line-exchange")
            .attr("d", exchangeLine);


        var exchangeCircle = d3.select('svg')
            .select('g.canvas')
            .selectAll('circle.exchange')
            .data(data, function (d) {
                return d.time;
            });

        exchangeCircle.exit().remove();

        exchangeCircle.enter()
            .append('circle')
            .attr('r', r)
            .attr('class', 'exchange')
            .attr('cx', function (d) {
                return xScale(months[d.date - 1]) + xScale.rangeBand() / 2;
            })
            .attr('cy', function (d) {
                return yScale(d.exchangeNumber);
            });

        var useCircle=d3.select('svg').select('g.canvas').selectAll('circle.use').data(data,function(d){return d.time;});

        useCircle.exit().remove();

        useCircle.enter()
            .append('circle')
            .attr('r', r)
            .attr('class', 'use')
            .attr('cx', function (d) {
                return xScale(months[d.date - 1]) + xScale.rangeBand() / 2;
            })
            .attr('cy', function (d) {
                return yScale(d.useNumber);
            });

        d3.select('text#use-text')
            .attr('x', function () {
                return xScale(months[data.length - 1]) + xScale.rangeBand() / 2;
            })
            .attr('y', function () {
                return yScale(data[data.length - 1].useNumber);
            });

        d3.select('text#exchange-text')
            .attr('x', function () {
                return xScale(months[data.length - 1]) + xScale.rangeBand() / 2;
            })
            .attr('y', function () {
                return yScale(data[data.length - 1].exchangeNumber);
            })
    }

    function draw(data) {
        var svg = d3.select('div.container').append('svg')
            .attr('width', width + margin.left + margin.right)
            .attr('height', height + margin.bottom + margin.top)
            .append('g')
            .attr('class', 'canvas')
            .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');
        var useExtent = d3.extent(data, function (d) {
            return d.useNumber;
        });

        var exchangeExtent = d3.extent(data, function (d) {
            return d.exchangeNumber;
        });

        yScale.domain([0, Math.max(useExtent[1], exchangeExtent[1])]);

        var xAxis = d3.svg.axis().scale(xScale).orient('bottom');
        var yAxis = d3.svg.axis().scale(yScale).orient('left');

        var useLine = d3.svg.line()
            .x(function (d, i) {
                return xScale(months[d.date - 1]) + xScale.rangeBand() / 2;
            })
            .y(function (d) {
                return yScale(d.useNumber);
            });

        var exchangeLine = d3.svg.line()
            .x(function (d, i) {
                return xScale(months[d.date - 1]) + xScale.rangeBand() / 2;
            })
            .y(function (d) {
                return yScale(d.exchangeNumber);
            });

        svg.append('g')
            .attr('id', 'xAxis')
            .attr('class', 'x axis')
            .attr("transform", "translate(0," + height + ")")
            .call(xAxis)
            .append('text')
            .text('月份')
            .attr('class','xlabel')
            .attr('x',width+margin.left)
            .attr('y','8')
            .attr("dy", ".71em")
            .style("text-anchor", "end");

        svg.append('g')
            .attr('id', 'yAxis')
            .attr('class', 'y axis')
            .call(yAxis)
            .append('text')
            .text('张数')
            .attr("transform", "rotate(-90)")
            .attr('x',width)
            .attr('class','xlabel')
            .style("text-anchor", "end");

        svg.append("path")
            .datum(data)
            .attr('id', 'line-use')
            .attr("class", "line-use")
            .attr("d", useLine);

        svg.append("path")
            .datum(data)
            .attr('id', 'line-exchange')
            .attr("class", "line-exchange")
            .attr("d", exchangeLine);

        svg.selectAll('circle.exchange')
            .data(data, function (d) {
                return d.time
            })
            .enter()
            .append('circle')
            .attr('r', r)
            .attr('class', 'exchange')
            .attr('cx', function (d) {
                return xScale(months[d.date - 1]) + xScale.rangeBand() / 2;
            })
            .attr('cy', function (d) {
                return yScale(d.exchangeNumber);
            });

        svg.selectAll('circle.use')
            .data(data, function (d) {
                return d.time
            })
            .enter()
            .append('circle')
            .attr('r', r)
            .attr('class', 'use')
            .attr('cx', function (d) {
                return xScale(months[d.date - 1]) + xScale.rangeBand() / 2;
            })
            .attr('cy', function (d) {
                return yScale(d.useNumber);
            });

        svg.append('text')
            .attr('id', 'use-text')
            .attr('dx', 10)
            .style('font-size', '24px')
            .style('fill', use_color)
            .attr('x', function () {
                return xScale(months[data.length - 1]) + xScale.rangeBand() / 2;
            })
            .attr('y', function () {
                return yScale(data[data.length - 1].useNumber);
            })
            .text('使用');

        svg.append('text')
            .attr('id', 'exchange-text')
            .style('fill', exchange_color)
            .attr('dx', 10)
            .style('font-size', '24px')
            .attr('x', function () {
                return xScale(months[data.length - 1]) + xScale.rangeBand() / 2;
            })
            .attr('y', function () {
                return yScale(data[data.length - 1].exchangeNumber);
            })
            .text('兑换');
    }
});

