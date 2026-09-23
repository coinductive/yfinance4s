package io.github.coinductive.yfinance4s.integration

import munit.CatsEffectSuite
import cats.effect.*
import io.github.coinductive.yfinance4s.{YFinanceClient, YFinanceClientConfig}
import io.github.coinductive.yfinance4s.models.Ticker
import scala.concurrent.duration.*

class YFinanceClientSpec extends CatsEffectSuite {

  val config: YFinanceClientConfig = YFinanceClientConfig(
    connectTimeout = 10.seconds,
    readTimeout = 30.seconds,
    retries = 3
  )

  test("returns symbol, longName, and exchangeName for AAPL") {
    YFinanceClient.resource[IO](config).use { client =>
      val ticker = Ticker("AAPL")

      client.charts.getStock(ticker).map { stockResultOpt =>
        assert(stockResultOpt.isDefined, "Stock result should be defined for AAPL")

        val stockResult = stockResultOpt.get
        assertEquals(stockResult.symbol, "AAPL")
        assert(
          stockResult.longName.contains("Apple"),
          s"Expected longName to contain 'Apple', got: ${stockResult.longName}"
        )
        assert(stockResult.exchangeName.nonEmpty, "Exchange name should not be empty")
      }
    }
  }

  test("returns fundamentals and PEG ratio for AAPL") {
    YFinanceClient.resource[IO](config).use { client =>
      val ticker = Ticker("AAPL")

      client.charts.getStock(ticker).map { stockResultOpt =>
        assert(stockResultOpt.isDefined, "Stock result should be defined for AAPL")

        val stockResult = stockResultOpt.get
        assert(stockResult.totalRevenue > 0L, "Total revenue should be positive")
        assert(stockResult.sharesOutstanding > 0L, "Shares outstanding should be positive")
        assert(stockResult.sector.isDefined, "Sector should be defined")
        assert(stockResult.pegRatio.isDefined, "PEG ratio should be defined")
      }
    }
  }

}
